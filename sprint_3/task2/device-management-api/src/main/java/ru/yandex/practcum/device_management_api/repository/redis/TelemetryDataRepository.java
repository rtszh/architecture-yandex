package ru.yandex.practcum.device_management_api.repository.redis;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;
import ru.yandex.practcum.device_management_api.model.redis.TelemetryData;

public interface TelemetryDataRepository extends CrudRepository<TelemetryData, String> {
}
