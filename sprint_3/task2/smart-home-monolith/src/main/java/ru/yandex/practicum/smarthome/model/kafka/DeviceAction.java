package ru.yandex.practicum.smarthome.model.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAction {
    private String action;
    private String value;
}
