package ru.yandex.practcum.device_management_api.model.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceTelemetryRequest {
    private Long accountId;
    private String serialNumber;
}
