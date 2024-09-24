package ru.yandex.practcum.device_management_api.model.rest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class DeviceTelemetryResponse {
    private String status;
    private Long accountId;
    private String serialNumber;
    private LocalDateTime updatedAt;
    private String data;
}
