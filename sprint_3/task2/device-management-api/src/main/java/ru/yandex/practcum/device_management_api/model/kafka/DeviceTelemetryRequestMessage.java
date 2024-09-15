package ru.yandex.practcum.device_management_api.model.kafka;

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
