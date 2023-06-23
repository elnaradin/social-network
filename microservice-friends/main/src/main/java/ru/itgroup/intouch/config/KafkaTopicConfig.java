package ru.itgroup.intouch.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KafkaTopicConfig {
    @Value("${spring.kafka.notification-event}")
    private String notificationTopic;

    @Bean
    public NewTopic getNotificationEventTopic() {
        return new NewTopic(notificationTopic, 1, (short) 1);
    }
}

