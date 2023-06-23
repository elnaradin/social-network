package ru.itgroup.intouch.aggregator.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.aggregator.controller.ws.NotificationHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class NotificationKafkaListener {
    private final NotificationHandler notificationHandler;

    @KafkaListener(topics = "${spring.kafka.notification-serv}")
    public void onMessage(String message) throws IOException {
        notificationHandler.sendMessage(message);
    }

    @KafkaListener(topics = "${spring.kafka.message-event}")
    public void onDialogMessage(String message) throws IOException {
        notificationHandler.sendDialogMessage(message);
    }
}
