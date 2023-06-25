package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.NotificationDto;

@Service
@RequiredArgsConstructor
public class NotificationKafkaProducer {
    @Value("${spring.kafka.notification-event}")
    private String notificationTopic;

    private final KafkaTemplate<Long, NotificationDto> kafkaTemplate;

    public void produce(Long authorId, Long entityId, NotificationType type) {
        NotificationDto dto = NotificationDto
                .builder()
                .authorId(authorId)
                .entityId(entityId)
                .notificationType(type.name())
                .build();

        kafkaTemplate.send(notificationTopic, dto);
    }
}
