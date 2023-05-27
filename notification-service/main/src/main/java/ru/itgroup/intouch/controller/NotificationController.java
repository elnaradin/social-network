package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.service.EmailSender;
import ru.itgroup.intouch.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}/notifications")
@Slf4j
public class NotificationController {
    private final NotificationService notificationService;
    private final EmailSender emailSender;

    @GetMapping("")
    public NotificationListDto getNotifications() {
        emailSender.send("anatolijv236@gmail.com", "Hello", "Hi there!");
        log.info("Method getNotifications is executing");
        return notificationService.getNotifications();
    }

    @GetMapping("/count")
    public NotificationCountDto getNotificationsCount() {
        return notificationService.countNewNotifications();
    }
}
