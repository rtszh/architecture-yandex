package ru.yandex.practicum.smarthome.model.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceActionMessage {
    private Long accountId;
    private String serialNumber;
    private DeviceAction deviceAction;
    private String messageId;
}
