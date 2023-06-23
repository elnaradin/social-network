package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.notifications.NotificationMessageDto;
import ru.itgroup.intouch.dto.notifications.WebSocketDto;

@Component
@RequiredArgsConstructor
public class NotificationsSender {

    @Value("${spring.kafka.notification-event}")
    private String notificationTopic;

    private final KafkaTemplate<Long, WebSocketDto> kafkaTemplate;

    public void send(Long authorID, Long receiverId) {
        NotificationMessageDto notificationMessageDto = new NotificationMessageDto();
        notificationMessageDto.setNotificationType(NotificationType.FRIEND_REQUEST.name());
        notificationMessageDto.setAuthorId(authorID);
        notificationMessageDto.setReceiverId(receiverId);
        kafkaTemplate.send(notificationTopic, notificationMessageDto);
    }
}
