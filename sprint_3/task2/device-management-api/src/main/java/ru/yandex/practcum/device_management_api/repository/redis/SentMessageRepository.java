package ru.yandex.practcum.device_management_api.repository.redis;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practcum.device_management_api.model.redis.SentMessage;

public interface SentMessageRepository extends CrudRepository<SentMessage, String> {
}
