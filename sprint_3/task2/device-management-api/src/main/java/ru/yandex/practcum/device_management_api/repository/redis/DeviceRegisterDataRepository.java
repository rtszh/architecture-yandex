package ru.yandex.practcum.device_management_api.repository.redis;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;

public interface DeviceRegisterDataRepository extends CrudRepository<DeviceRegisterData, String> {
}
