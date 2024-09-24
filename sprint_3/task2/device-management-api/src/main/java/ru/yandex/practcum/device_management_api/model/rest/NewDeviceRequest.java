package ru.yandex.practcum.device_management_api.model.rest;

import lombok.Data;

@Data
public class NewDeviceRequest {
    private Long accountId;
    private String serialNumber;
}
