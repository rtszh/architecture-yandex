package ru.yandex.practicum.smarthome.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.smarthome.properties.AppKafkaProperties;

@Configuration
@EnableConfigurationProperties(AppKafkaProperties.class)
public class ConfigurationProperties {
}
