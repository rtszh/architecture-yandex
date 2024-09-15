package ru.yandex.practicum.smarthome.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.smarthome.entity.HeatingSystem;
import ru.yandex.practicum.smarthome.messaging.KafkaSender;
import ru.yandex.practicum.smarthome.model.kafka.DeviceAction;
import ru.yandex.practicum.smarthome.model.kafka.DeviceActionMessage;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActionMessageAdapter {
    private static final String TOPIC = "device-action-request";
    private final KafkaSender kafkaSender;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void publishAction(HeatingSystem heatingSystem) {
        final var messageId = UUID.randomUUID().toString();
        final var deviceActionMessage = DeviceActionMessage.builder()
                .accountId(heatingSystem.getAccountId())
                .serialNumber(heatingSystem.getSerialNumber())
                .messageId(messageId)
                .deviceAction(createDeviceAction(heatingSystem))
                .build();

        kafkaSender.send(TOPIC, mapper.writeValueAsString(deviceActionMessage));
    }

    private DeviceAction createDeviceAction(HeatingSystem heatingSystem) {
        DeviceAction deviceAction = new DeviceAction();
        deviceAction.setAction("POWER");

        if(heatingSystem.isOn()) {
            deviceAction.setValue("ON");
        } else {
            deviceAction.setValue("OFF");
        }

        return deviceAction;
    }
}
