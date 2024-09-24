package ru.yandex.practcum.device_management_api.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practcum.device_management_api.model.db.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
