package ru.yandex.practicum.smarthome.model.kafka;

import lombok.Data;

import java.util.List;

@Data
public class DeviceActionList {
    private String action;
    private List<String> value;
}
