package ru.yandex.practcum.device_management_api.model.rest;

import lombok.Data;

@Data
public class DeviceActionRequest {
    private Long accountId;
    private String serialNumber;
    private String actionType;
    private String actionValue;
}
