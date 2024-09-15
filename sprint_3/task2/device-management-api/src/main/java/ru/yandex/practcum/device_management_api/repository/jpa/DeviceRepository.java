package ru.yandex.practcum.device_management_api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practcum.device_management_api.model.db.Account;
import ru.yandex.practcum.device_management_api.model.db.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
