package ru.itgroup.intouch.aggregator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.client.NotificationServiceClient;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}/notifications")
public class NotificationController {
    private final NotificationServiceClient client;

    @GetMapping("")
    public NotificationListDto getNotifications() {
        return client.feignGetNotifications();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNotification(@RequestBody @Valid NotificationRequestDto notificationRequestDto) {
        return client.feignCreateNotification(notificationRequestDto);
    }

    @GetMapping("/count")
    public NotificationCountDto getNotificationsCount() {
        return client.feignGetNotificationsCount();
    }

    @GetMapping("/settings")
    public List<?> getSettings() {
        return client.feignGetSettings();
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateSettings(@RequestBody @Valid NotificationSettingsDto notificationSettingsDto) {
        return client.feignUpdateSettings(notificationSettingsDto);
    }
}
