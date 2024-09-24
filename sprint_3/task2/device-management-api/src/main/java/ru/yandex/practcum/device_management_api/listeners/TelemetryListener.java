package ru.yandex.practcum.device_management_api.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataResponseMessage;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceTelemetryResponseMessage;
import ru.yandex.practcum.device_management_api.service.DeviceTelemetryService;

@Component
@RequiredArgsConstructor
public class TelemetryListener {

    private final DeviceTelemetryService deviceTelemetryService;
    private final ObjectMapper mapper;

    @KafkaListener(groupId = "1", topics = "device-telemetry-response")
    @SneakyThrows
    public void listen(String message) {
        deviceTelemetryService.processTelemetryResponseMessage(mapper.readValue(message, DeviceTelemetryResponseMessage.class));
    }
}
