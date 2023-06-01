package ru.itgroup.intouch.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itgroup.intouch.service.notification.sender.KafkaObserver;
import ru.itgroup.intouch.service.notification.sender.NotificationMailObserver;
import ru.itgroup.intouch.service.notification.sender.NotificationSender;

@Configuration
@RequiredArgsConstructor
public class ObserverConfig {
    private final KafkaObserver kafkaObserver;
    private final NotificationMailObserver notificationMailObserver;

    @Bean
    public NotificationSender getNotificationSender() {
        NotificationSender notificationSender = new NotificationSender();
        notificationSender.subscribe(KafkaObserver.class.getSimpleName(), kafkaObserver);
        notificationSender.subscribe(NotificationMailObserver.class.getSimpleName(), notificationMailObserver);

        return notificationSender;
    }
}
