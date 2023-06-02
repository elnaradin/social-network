package ru.itgroup.intouch.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.annotation.Loggable;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.service.NotificationCreatorService;

@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {
    private final NotificationCreatorService notificationCreatorService;

    @Loggable
    @KafkaListener(topics = "${spring.kafka.notification-event}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(NotificationRequestDto dto) throws ClassNotFoundException {
        notificationCreatorService.createNotification(dto);
    }
}
