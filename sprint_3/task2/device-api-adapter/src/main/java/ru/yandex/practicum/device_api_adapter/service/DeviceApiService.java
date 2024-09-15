package ru.yandex.practicum.device_api_adapter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.device_api_adapter.model.DeviceActionMessage;
import ru.yandex.practicum.device_api_adapter.model.DeviceRegisterDataRequestMessage;
import ru.yandex.practicum.device_api_adapter.model.DeviceTelemetryRequestMessage;
import ru.yandex.practicum.device_api_adapter.model.DeviceTelemetryResponseMessage;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeviceApiService {

    private static final String ALL_OPTION = "ALL";
    private static final String LATEST_OPTION = "LATEST";

    private final MockResult mockResult;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void registerDevice(DeviceRegisterDataRequestMessage deviceRegisterDataRequestMessage) {
        final var deviceInfo = mockResult.deviceRegisterDataResponseMessage();

        final var message = deviceInfo.toBuilder()
                .messageId(deviceRegisterDataRequestMessage.getMessageId())
                .serialNumber(deviceRegisterDataRequestMessage.getSerialNumber())
                .accountId(deviceRegisterDataRequestMessage.getAccountId())
                .build();

        kafkaTemplate.send("device-register-data-response", objectMapper.writeValueAsString(message));
    }

    @SneakyThrows
    public void getTelemetryData(DeviceTelemetryRequestMessage deviceTelemetryRequestMessage) {
        final var option = deviceTelemetryRequestMessage.getOption();

        String data;
        LocalDateTime time;

        switch (option) {
            case ALL_OPTION -> {
                final var res = mockResult.deviceAllTelemetry();
                data = res.getKey();
                time = res.getValue();
            }
            case LATEST_OPTION -> {
                final var res = mockResult.deviceLatestTelemetry();
                data = res.getKey();
                time = res.getValue();
            }
            default -> throw new UnsupportedOperationException();
        }

        final var message = DeviceTelemetryResponseMessage.builder()
                .accountId(deviceTelemetryRequestMessage.getAccountId())
                .data(data)
                .messageId(deviceTelemetryRequestMessage.getMessageId())
                .updatedAt(time)
                .serialNumber(deviceTelemetryRequestMessage.getSerialNumber())
                .build();
        kafkaTemplate.send("device-telemetry-response", objectMapper.writeValueAsString(message));
    }

    public void action(DeviceActionMessage deviceActionMessage) {
        mockResult.doAction(deviceActionMessage.getDeviceAction());
    }
}
