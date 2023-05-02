package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;

import java.util.List;

@FeignClient(name = "notification-service", url = "${SN_NOTIFICATIONS_HOST}:${SN_NOTIFICATIONS_PORT}", path = "/api/v1/notifications")
public interface NotificationServiceClient {
    @GetMapping("")
    NotificationListDto feignGetNotifications();

    @PostMapping("/add")
    ResponseEntity<?> feignCreateNotification(NotificationRequestDto notificationRequestDto);

    @GetMapping("/count")
    NotificationCountDto feignGetNotificationsCount();

    @GetMapping("/settings")
    List<?> feignGetSettings();

    @PutMapping("/settings")
    ResponseEntity<?> feignUpdateSettings(NotificationSettingsDto notificationSettingsDto);
}
