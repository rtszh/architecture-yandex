package ru.yandex.practcum.device_management_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practcum.device_management_api.mappers.DeviceRegisterDataMapper;
import ru.yandex.practcum.device_management_api.model.db.Account;
import ru.yandex.practcum.device_management_api.model.db.Device;
import ru.yandex.practcum.device_management_api.model.db.DeviceAction;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataRequestMessage;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataResponseMessage;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;
import ru.yandex.practcum.device_management_api.model.redis.SentMessage;
import ru.yandex.practcum.device_management_api.model.rest.DeviceRegisterDataResponse;
import ru.yandex.practcum.device_management_api.model.rest.DeviceResponse;
import ru.yandex.practcum.device_management_api.model.rest.NewDeviceRequest;
import ru.yandex.practcum.device_management_api.repository.jpa.AccountRepository;
import ru.yandex.practcum.device_management_api.repository.jpa.DeviceActionRepository;
import ru.yandex.practcum.device_management_api.repository.jpa.DeviceRepository;
import ru.yandex.practcum.device_management_api.repository.redis.DeviceRegisterDataRepository;
import ru.yandex.practcum.device_management_api.repository.redis.SentMessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DeviceRegistrationService {

    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceActionRepository deviceActionRepository;

    private final DeviceRegisterDataRepository deviceRegisterDataRepository;
    private final SentMessageRepository sentMessageRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;
    private final DeviceRegisterDataMapper deviceRegisterDataMapper;

    @SneakyThrows
    @Transactional
    public CompletableFuture<DeviceResponse> registerDevice(NewDeviceRequest request) {
        final Account account = accountRepository.findById(request.getAccountId()).get();
        final var messageId = UUID.randomUUID().toString();
        final var deviceRegisterDataRequest = DeviceRegisterDataRequestMessage.builder()
                .accountId(account.getId())
                .serialNumber(request.getSerialNumber())
                .messageId(messageId)
                .build();

        SentMessage message = new SentMessage(messageId, true);
        sentMessageRepository.save(message);

        return kafkaTemplate.send("device-register-data-request", objectMapper.writeValueAsString(deviceRegisterDataRequest))
                .thenApply(ignored -> new DeviceResponse(account.getId(), request.getSerialNumber(), messageId));
    }

    @Transactional
    public void processRegisteredDeviceMessage(DeviceRegisterDataResponseMessage deviceRegisterDataResponseMessage) {
        final Account account = accountRepository.findById(deviceRegisterDataResponseMessage.getAccountId()).get();
        final Device device = saveDevice(deviceRegisterDataResponseMessage, account);
        saveDeviceActions(deviceRegisterDataResponseMessage, device);

        final var redisDeviceRegisterDataData = deviceRegisterDataMapper.toDeviceRegisterDataResponse(deviceRegisterDataResponseMessage);
        deviceRegisterDataRepository.save(redisDeviceRegisterDataData);
    }

    public DeviceRegisterDataResponse findRegisterDataByMessageId(String messageId) {
        final Optional<SentMessage> sentMessage = sentMessageRepository.findById(messageId);

        if (sentMessage.isEmpty()) {
            return DeviceRegisterDataResponse.builder()
                    .status("UNKNOWN_MESSAGE")
                    .build();
        }

        final Optional<DeviceRegisterData> data = deviceRegisterDataRepository.findById(messageId);

        if (data.isEmpty()) {
            return DeviceRegisterDataResponse.builder()
                    .status("AWAIT_FOR_DATA")
                    .build();
        }

        final var response = deviceRegisterDataMapper.toDeviceRegisterDataResponse(data.get());

        return response.toBuilder()
                .status("SUCCESS")
                .build();
    }

    private Device saveDevice(DeviceRegisterDataResponseMessage message, Account account) {
        Device device = new Device();
        device.setName(message.getName());
        device.setDescription(message.getDescription());
        device.setSerialNumber(message.getSerialNumber());
        device.setDeviceType(message.getDeviceType());
        device.setAccount(account);
        return deviceRepository.save(device);
    }

    private List<DeviceAction> saveDeviceActions(DeviceRegisterDataResponseMessage deviceRegisterDataResponseMessage, Device device) {
        final var deviceActions =  deviceRegisterDataResponseMessage.getDeviceActions().stream()
                .flatMap(action -> createDeviceActionEntity(action, device).stream())
                .toList();

        return deviceActionRepository.saveAll(deviceActions);
    }

    private List<DeviceAction> createDeviceActionEntity(ru.yandex.practcum.device_management_api.model.kafka.DeviceActionList action, Device device) {
        return action.getValue().stream()
                .map(value -> {
                    final var deviceAction = new DeviceAction();
                    deviceAction.setAction(action.getAction());
                    deviceAction.setValue(value);
                    deviceAction.setDevice(device);
                    return deviceAction;
                })
                .toList();
    }
}
