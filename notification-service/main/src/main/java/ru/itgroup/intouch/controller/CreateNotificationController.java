package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.service.NotificationCreatorService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class CreateNotificationController {
    private final NotificationCreatorService notificationCreatorService;

    @PostMapping("/add")
    public ResponseEntity<?> createNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        notificationCreatorService.createNotification(notificationRequestDto);
        return ResponseEntity.ok("");
    }
}
