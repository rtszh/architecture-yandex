package ru.yandex.practicum.device_api_adapter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
@Data
public class AppKafkaProperties {
    private String host;
    private String port;
}
