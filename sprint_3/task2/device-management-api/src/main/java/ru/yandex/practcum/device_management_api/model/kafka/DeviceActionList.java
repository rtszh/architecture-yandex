package ru.yandex.practcum.device_management_api.model.kafka;

import lombok.Data;

import java.util.List;

@Data
public class DeviceActionList {
    private String action;
    private List<String> value;
}
