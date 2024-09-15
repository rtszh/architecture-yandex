package ru.yandex.practcum.device_management_api.model.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceActionMessage {
    private Long accountId;
    private String serialNumber;
    private DeviceAction deviceAction;
    private String messageId;
}
