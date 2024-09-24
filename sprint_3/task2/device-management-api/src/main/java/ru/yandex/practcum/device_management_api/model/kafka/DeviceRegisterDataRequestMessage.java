package ru.yandex.practcum.device_management_api.model.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceRegisterDataRequestMessage {
    private Long accountId;
    private String serialNumber;
    private String messageId;
}
