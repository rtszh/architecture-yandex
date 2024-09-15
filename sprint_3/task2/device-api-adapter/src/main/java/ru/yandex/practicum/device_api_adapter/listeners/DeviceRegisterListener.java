package ru.yandex.practicum.device_api_adapter.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.device_api_adapter.model.DeviceRegisterDataRequestMessage;
import ru.yandex.practicum.device_api_adapter.service.DeviceApiService;

@Component
@RequiredArgsConstructor
public class DeviceRegisterListener {

    private final DeviceApiService deviceApiService;
    private final ObjectMapper mapper;

    @KafkaListener(groupId = "2", topics = "device-register-data-request")
    @SneakyThrows
    public void listen(String message) {
        deviceApiService.registerDevice(mapper.readValue(message, DeviceRegisterDataRequestMessage.class));
    }
}
