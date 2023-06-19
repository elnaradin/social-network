package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;

@FeignClient(name = "notification-service", url = "${SN_NOTIFICATIONS_HOST}:${SN_NOTIFICATIONS_PORT}", path = "${server.api.prefix}/notifications")
public interface NotificationServiceClient {
    @GetMapping("")
    String feignGetNotifications(@RequestParam Long userId);

    @PostMapping("/add")
    ResponseEntity<?> feignCreateNotification(NotificationRequestDto notificationRequestDto, @RequestParam Long userId);

    @GetMapping("/count")
    String feignGetNotificationsCount(@RequestParam Long userId);

    @GetMapping("/settings")
    String feignGetSettings(@RequestParam Long userId);

    @PutMapping("/settings")
    ResponseEntity<?> feignUpdateSettings(NotificationSettingsDto notificationSettingsDto, @RequestParam Long userId);
}
