package ru.yandex.practcum.device_management_api.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DeviceAction {
    private String action;
    private String value;
}
