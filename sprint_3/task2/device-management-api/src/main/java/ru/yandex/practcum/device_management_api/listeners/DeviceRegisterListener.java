package ru.yandex.practcum.device_management_api.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataResponseMessage;
import ru.yandex.practcum.device_management_api.service.DeviceRegistrationService;

@Component
@RequiredArgsConstructor
public class DeviceRegisterListener {

    private final DeviceRegistrationService deviceRegistrationService;
    private final ObjectMapper mapper;

    @KafkaListener(groupId = "1", topics = "device-register-data-response")
    @SneakyThrows
    public void listen(String message) {
        deviceRegistrationService.processRegisteredDeviceMessage(mapper.readValue(message, DeviceRegisterDataResponseMessage.class));
    }
}
