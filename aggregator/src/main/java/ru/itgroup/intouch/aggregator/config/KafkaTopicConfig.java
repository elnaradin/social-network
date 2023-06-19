package ru.itgroup.intouch.aggregator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KafkaTopicConfig {
    @Value("${spring.kafka.message-serv}")
    private String messageTopic;

    @Bean
    public NewTopic getMessageServTopic() {
        return new NewTopic(messageTopic, 1, (short) 1);
    }
}
