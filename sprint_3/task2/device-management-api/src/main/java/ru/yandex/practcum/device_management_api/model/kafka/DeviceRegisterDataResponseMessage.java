package ru.yandex.practcum.device_management_api.model.kafka;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class DeviceRegisterDataResponseMessage {
    private Long accountId;
    private String serialNumber;
    private String name;
    private String description;
    private String deviceType;
    private List<DeviceActionList> deviceActions;
    private String messageId;
}
