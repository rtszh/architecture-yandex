package ru.yandex.practicum.device_api_adapter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceTelemetryResponseMessage {
    private Long accountId;
    private String data;
    private String serialNumber;
    private LocalDateTime updatedAt;
    private String messageId;
}
