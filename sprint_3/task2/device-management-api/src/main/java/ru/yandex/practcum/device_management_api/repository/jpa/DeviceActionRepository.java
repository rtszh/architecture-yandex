package ru.yandex.practcum.device_management_api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practcum.device_management_api.model.db.Device;
import ru.yandex.practcum.device_management_api.model.db.DeviceAction;

public interface DeviceActionRepository extends JpaRepository<DeviceAction, Long> {
}
