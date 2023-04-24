package ru.itgroup.intouch.controller;

import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private final NotificationService notificationService;

    @GetMapping("")
    public NotificationListDto getNotifications() {
        log.info("Method getNotifications is executing");
        return notificationService.getNotifications();
    }

    @GetMapping("/count")
    public NotificationCountDto getNotificationsCount() {
        return notificationService.countNewNotifications();
    }
}
