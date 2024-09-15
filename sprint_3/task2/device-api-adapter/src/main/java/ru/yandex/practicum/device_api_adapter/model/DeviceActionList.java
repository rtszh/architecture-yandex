package ru.yandex.practicum.device_api_adapter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeviceActionList {
    private String action;
    private List<String> value;
}
