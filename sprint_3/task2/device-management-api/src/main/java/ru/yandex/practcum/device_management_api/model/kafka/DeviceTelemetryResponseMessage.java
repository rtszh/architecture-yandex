package ru.yandex.practcum.device_management_api.model.kafka;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceTelemetryResponseMessage {
    private Long accountId;
    private String data;
    private String serialNumber;
    private LocalDateTime updatedAt;
    private String messageId;
}
