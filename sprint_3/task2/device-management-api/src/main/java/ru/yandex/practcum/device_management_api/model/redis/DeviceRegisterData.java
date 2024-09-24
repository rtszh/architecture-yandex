package ru.yandex.practcum.device_management_api.model.redis;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceAction;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceActionList;

import java.util.List;

@RedisHash(value = "DeviceRegisterData", timeToLive = 300L)
@Data
@Builder(toBuilder = true)
public class DeviceRegisterData {
    @Id
    private String messageId;
    private Long accountId;
    private String serialNumber;
    private String deviceName;
    private String description;
    private String deviceType;
    private List<DeviceActionList> deviceActions;
}
