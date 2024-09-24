package ru.yandex.practcum.device_management_api.model.redis;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
@RedisHash(value = "TelemetryData", timeToLive = 300L)
@Data
@Builder(toBuilder = true)
public class TelemetryData {
    @Id
    private String messageId;
    private Long accountId;
    private String serialNumber;
    private LocalDateTime updatedAt;
    private String data;
}
