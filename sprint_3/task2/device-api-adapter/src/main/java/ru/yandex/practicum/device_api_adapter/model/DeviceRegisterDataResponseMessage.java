package ru.yandex.practicum.device_api_adapter.model;

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
