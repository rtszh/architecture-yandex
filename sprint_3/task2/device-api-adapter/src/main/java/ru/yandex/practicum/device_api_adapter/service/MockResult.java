package ru.yandex.practicum.device_api_adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.device_api_adapter.model.DeviceAction;
import ru.yandex.practicum.device_api_adapter.model.DeviceActionList;
import ru.yandex.practicum.device_api_adapter.model.DeviceRegisterDataResponseMessage;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MockResult {
    public DeviceRegisterDataResponseMessage deviceRegisterDataResponseMessage() {
        return DeviceRegisterDataResponseMessage.builder()
                .name("device1")
                .description("description for device1")
                .deviceType("LIGHTING_DEVICE")
                .deviceActions(deviceActions())
                .build();
    }

    private List<DeviceActionList> deviceActions() {
        return List.of(new DeviceActionList("POWER", List.of("TURN_ON", "TURN_OFF")));
    }

    public Map.Entry<String, LocalDateTime> deviceAllTelemetry() {
        return new AbstractMap.SimpleEntry<>("All telemetry", LocalDateTime.now());
    }

    public Map.Entry<String, LocalDateTime> deviceLatestTelemetry() {
        return new AbstractMap.SimpleEntry<>("Latest telemetry", LocalDateTime.now());
    }

    public void doAction(DeviceAction deviceAction) {
        log.info("complete action: {} ", deviceAction.getAction());
        if (deviceAction.getValue() != null) {
            log.info("new value: {} ", deviceAction.getValue());
        }
    }
}
