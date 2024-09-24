package ru.yandex.practcum.device_management_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practcum.device_management_api.mappers.TelemetryDataMapper;
import ru.yandex.practcum.device_management_api.model.db.Account;
import ru.yandex.practcum.device_management_api.model.kafka.*;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;
import ru.yandex.practcum.device_management_api.model.redis.SentMessage;
import ru.yandex.practcum.device_management_api.model.redis.TelemetryData;
import ru.yandex.practcum.device_management_api.model.rest.DeviceRegisterDataResponse;
import ru.yandex.practcum.device_management_api.model.rest.DeviceResponse;
import ru.yandex.practcum.device_management_api.model.rest.DeviceTelemetryRequest;
import ru.yandex.practcum.device_management_api.model.rest.DeviceTelemetryResponse;
import ru.yandex.practcum.device_management_api.repository.jpa.AccountRepository;
import ru.yandex.practcum.device_management_api.repository.jpa.DeviceActionRepository;
import ru.yandex.practcum.device_management_api.repository.jpa.DeviceRepository;
import ru.yandex.practcum.device_management_api.repository.redis.SentMessageRepository;
import ru.yandex.practcum.device_management_api.repository.redis.TelemetryDataRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class DeviceTelemetryService {

    private final AccountRepository accountRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceActionRepository deviceActionRepository;

    private final TelemetryDataRepository telemetryDataRepository;
    private final SentMessageRepository sentMessageRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;
    private final TelemetryDataMapper telemetryDataMapper;


    @SneakyThrows
    @Transactional
    public CompletableFuture<DeviceResponse> requestTelemetry(DeviceTelemetryRequest request, String option) {
        final Account account = accountRepository.findById(request.getAccountId()).get();
        final var messageId = UUID.randomUUID().toString();
        final var deviceTelemetryRequestMessage = DeviceTelemetryRequestMessage.builder()
                .accountId(account.getId())
                .serialNumber(request.getSerialNumber())
                .messageId(messageId)
                .option(option)
                .build();

        SentMessage message = new SentMessage(messageId, true);
        sentMessageRepository.save(message);

        return kafkaTemplate.send("device-telemetry-request", objectMapper.writeValueAsString(deviceTelemetryRequestMessage))
                .thenApply(ignored -> new DeviceResponse(account.getId(), request.getSerialNumber(), messageId));
    }

    @Transactional
    public void processTelemetryResponseMessage(DeviceTelemetryResponseMessage deviceRegisterDataResponseMessage) {
        final var redisData = telemetryDataMapper.toTelemetryData(deviceRegisterDataResponseMessage);
        telemetryDataRepository.save(redisData);
    }

    public DeviceTelemetryResponse findRegisterDataByMessageId(String messageId) {
        final Optional<SentMessage> sentMessage = sentMessageRepository.findById(messageId);

        if (sentMessage.isEmpty()) {
            return DeviceTelemetryResponse.builder()
                    .status("UNKNOWN_MESSAGE")
                    .build();
        }

        final Optional<TelemetryData> data = telemetryDataRepository.findById(messageId);

        if (data.isEmpty()) {
            return DeviceTelemetryResponse.builder()
                    .status("AWAIT_FOR_DATA")
                    .build();
        }

        final var response = telemetryDataMapper.toDeviceRegisterDataResponse(data.get());

        return response.toBuilder()
                .status("SUCCESS")
                .build();
    }
}
