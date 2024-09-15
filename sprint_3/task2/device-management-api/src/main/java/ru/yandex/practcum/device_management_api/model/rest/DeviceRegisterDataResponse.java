package ru.yandex.practcum.device_management_api.model.rest;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceAction;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceActionList;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class DeviceRegisterDataResponse {
    private String status;
    private Long accountId;
    private String serialNumber;
    private String deviceName;
    private String description;
    private String deviceType;
    private List<DeviceActionList> deviceActions;
}
