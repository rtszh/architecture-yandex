package ru.yandex.practicum.device_api_adapter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.device_api_adapter.properties.AppKafkaProperties;

@Configuration
@EnableConfigurationProperties(AppKafkaProperties.class)
public class ConfigurationProperties {
}
