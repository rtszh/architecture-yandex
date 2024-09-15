package ru.yandex.practcum.device_management_api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practcum.device_management_api.properties.AppKafkaProperties;
import ru.yandex.practcum.device_management_api.properties.AppRedisProperties;

@Configuration
@EnableConfigurationProperties({AppKafkaProperties.class, AppRedisProperties.class})
public class ConfigurationProperties {
}
