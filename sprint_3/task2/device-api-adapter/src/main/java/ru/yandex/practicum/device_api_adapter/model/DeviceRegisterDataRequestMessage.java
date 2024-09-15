package ru.yandex.practicum.device_api_adapter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceRegisterDataRequestMessage {
    private Long accountId;
    private String serialNumber;
    private String messageId;
}
