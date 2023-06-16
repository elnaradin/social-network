package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;

@FeignClient(name = "notification-service", url = "${SN_NOTIFICATIONS_HOST}:${SN_NOTIFICATIONS_PORT}", path = "${server.api.prefix}/notifications")
public interface NotificationServiceClient {
    @GetMapping("")
    String feignGetNotifications();

    @PostMapping("/add")
    ResponseEntity<?> feignCreateNotification(NotificationRequestDto notificationRequestDto);

    @GetMapping("/count")
    String feignGetNotificationsCount();

    @GetMapping("/settings")
    String feignGetSettings();

    @PutMapping("/settings")
    ResponseEntity<?> feignUpdateSettings(NotificationSettingsDto notificationSettingsDto);
}
