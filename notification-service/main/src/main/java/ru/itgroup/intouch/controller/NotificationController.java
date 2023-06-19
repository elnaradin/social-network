package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.annotation.Loggable;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}/notifications")
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;

    @Loggable
    @GetMapping("")
    public NotificationListDto getNotifications(@RequestParam Long userId) {
        return notificationService.getNotifications(userId);
    }

    @Loggable
    @GetMapping("/count")
    public NotificationCountDto getNotificationsCount(@RequestParam Long userId) {
        return notificationService.countNewNotifications(userId);
    }
}
