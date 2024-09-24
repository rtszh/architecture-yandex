package ru.yandex.practcum.device_management_api.model.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "SentMessage", timeToLive = 300L)
@Data
@AllArgsConstructor
public class SentMessage {
    @Id
    private String messageId;
    private Boolean exist;
}
