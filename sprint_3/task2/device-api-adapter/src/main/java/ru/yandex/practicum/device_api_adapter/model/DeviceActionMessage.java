package ru.yandex.practicum.device_api_adapter.model;

import lombok.Data;

@Data
public class DeviceActionMessage {
    private Long accountId;
    private String serialNumber;
    private DeviceAction deviceAction;
    private String messageId;
}
