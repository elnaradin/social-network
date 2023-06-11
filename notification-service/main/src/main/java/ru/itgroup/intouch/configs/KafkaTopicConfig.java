package ru.itgroup.intouch.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KafkaTopicConfig {
    @Value("${spring.kafka.notification-serv}")
    private String topic;

    @Bean
    public NewTopic getNotificationServTopic() {
        return new NewTopic(topic, 1, (short) 1);
    }
}
