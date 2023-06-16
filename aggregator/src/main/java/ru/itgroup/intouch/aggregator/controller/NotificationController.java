package ru.itgroup.intouch.aggregator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}/notifications")
public class NotificationController {
    private final NotificationServiceClient client;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNotifications() {
        return client.feignGetNotifications();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNotification(@RequestBody @Valid NotificationRequestDto notificationRequestDto) {
        return client.feignCreateNotification(notificationRequestDto);
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNotificationsCount() {
        return client.feignGetNotificationsCount();
    }

    @GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSettings() {
        return client.feignGetSettings();
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateSettings(@RequestBody @Valid NotificationSettingsDto notificationSettingsDto) {
        return client.feignUpdateSettings(notificationSettingsDto);
    }
}
