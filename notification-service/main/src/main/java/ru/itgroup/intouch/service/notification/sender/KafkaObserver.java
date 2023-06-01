package ru.itgroup.intouch.service.notification.sender;

import lombok.RequiredArgsConstructor;
import model.Notification;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.notification.sender.NotificationObserver;
import ru.itgroup.intouch.dto.response.WsDto;
import ru.itgroup.intouch.mapper.NotificationMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaObserver implements NotificationObserver {
    @Value("${spring.kafka.notification-serv}")
    private String topic;

    private final KafkaTemplate<Long, WsDto> kafkaTemplate;
    private final NotificationMapper notificationMapper;

    @Override
    public void send(Notification notification) {
        WsDto wsDto = new WsDto(notificationMapper.getDestination(notification));
        kafkaTemplate.send(topic, wsDto);
    }

    @Override
    public void send(@NotNull List<Notification> notifications) {
        notifications.forEach(this::send);
    }
}
