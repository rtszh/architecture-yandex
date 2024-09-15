package ru.yandex.practcum.device_management_api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.redis")
@Data
public class AppRedisProperties {
    private String host;
    private String port;
}
