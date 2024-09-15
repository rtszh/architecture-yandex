package ru.yandex.practicum.device_api_adapter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeviceAction {
    private String action;
    private String value;
}
