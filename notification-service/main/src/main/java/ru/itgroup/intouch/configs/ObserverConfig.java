package ru.itgroup.intouch.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itgroup.intouch.service.notification.sender.NotificationMailObserver;
import ru.itgroup.intouch.service.notification.sender.NotificationSender;
import ru.itgroup.intouch.service.notification.sender.NotificationWsObserver;

@Configuration
@RequiredArgsConstructor
public class ObserverConfig {
    private final NotificationMailObserver notificationMailObserver;
    private final NotificationWsObserver notificationWsObserver;

    @Bean
    public NotificationSender getNotificationSender() {
        NotificationSender notificationSender = new NotificationSender();
        notificationSender.subscribe(NotificationWsObserver.class.getSimpleName(), notificationWsObserver);
        // TODO: раскомментировать, когда будет решен вопрос с СМТП на сервере
//        notificationSender.subscribe(NotificationMailObserver.class.getSimpleName(), notificationMailObserver);

        return notificationSender;
    }
}
