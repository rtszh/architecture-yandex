package ru.yandex.practcum.device_management_api.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import ru.yandex.practcum.device_management_api.properties.AppKafkaProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaAdminConfig {

    private final AppKafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                "%s:%s".formatted(kafkaProperties.getHost(), kafkaProperties.getPort())
        );
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("device-register-data-request")
                        .build(),
                TopicBuilder.name("device-register-data-response")
                        .build(),
                TopicBuilder.name("device-action")
                        .build(),
                TopicBuilder.name("device-telemetry-response")
                        .build()
        );
    }
}
