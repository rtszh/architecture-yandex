package ru.yandex.practicum.device_api_adapter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceTelemetryRequestMessage {
    private Long accountId;
    private String serialNumber;
    private String option;
    private String messageId;
}
