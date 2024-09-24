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
import ru.yandex.practcum.device_management_api.model.kafka.DeviceAction;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceActionMessage;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataRequestMessage;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataResponseMessage;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;
import ru.yandex.practcum.device_management_api.model.redis.SentMessage;
import ru.yandex.practcum.device_management_api.model.rest.*;
import ru.yandex.practcum.device_management_api.repository.jpa.AccountRepository;
import ru.yandex.practcum.device_management_api.repository.jpa.DeviceActionRepository;
import ru.yandex.practcum.device_management_api.repository.jpa.DeviceRepository;
import ru.yandex.practcum.device_management_api.repository.redis.DeviceRegisterDataRepository;
import ru.yandex.practcum.device_management_api.repository.redis.SentMessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
@RequiredArgsConstructor
public class DeviceActionService {

    private final AccountRepository accountRepository;

    private final SentMessageRepository sentMessageRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Transactional
    public CompletableFuture<DeviceActionResponse> action(DeviceActionRequest request) {
        final Account account = accountRepository.findById(request.getAccountId()).get();
        final var messageId = UUID.randomUUID().toString();
        final var deviceActionMessage = DeviceActionMessage.builder()
                .accountId(account.getId())
                .serialNumber(request.getSerialNumber())
                .messageId(messageId)
                .deviceAction(new DeviceAction(request.getActionType(), request.getActionValue()))
                .build();

        SentMessage message = new SentMessage(messageId, true);
        sentMessageRepository.save(message);

        return kafkaTemplate.send("device-action-request", objectMapper.writeValueAsString(deviceActionMessage))
                .thenApply(ignored -> new DeviceActionResponse("SUCCESS"));
    }
}
