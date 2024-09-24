package ru.yandex.practcum.device_management_api.model.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceResponse {
    private Long accountId;
    private String serialNumber;
    private String messageId;
}
