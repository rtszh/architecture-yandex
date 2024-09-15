package ru.yandex.practicum.device_api_adapter.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.device_api_adapter.model.DeviceActionMessage;
import ru.yandex.practicum.device_api_adapter.model.DeviceRegisterDataRequestMessage;
import ru.yandex.practicum.device_api_adapter.service.DeviceApiService;

@Component
@RequiredArgsConstructor
public class DeviceActionListener {

    private final DeviceApiService deviceApiService;
    private final ObjectMapper mapper;

    @KafkaListener(groupId = "2", topics = "device-action-request")
    @SneakyThrows
    public void listen(String message) {
        deviceApiService.action(mapper.readValue(message, DeviceActionMessage.class));
    }
}
