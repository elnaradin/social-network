package ru.itgroup.intouch.aggregator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.aggregator.config.security.jwt.JWTUtil;
import ru.itgroup.intouch.client.NotificationServiceClient;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}/notifications")
public class NotificationController {
    private final NotificationServiceClient client;
    private final JWTUtil jwtUtil;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNotifications(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtUtil.getUserIdFromHeader(authorizationHeader);
        return client.feignGetNotifications(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createNotification(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody @Valid NotificationRequestDto notificationRequestDto) {
        Long userId = jwtUtil.getUserIdFromHeader(authorizationHeader);
        return client.feignCreateNotification(notificationRequestDto, userId);
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNotificationsCount(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtUtil.getUserIdFromHeader(authorizationHeader);
        return client.feignGetNotificationsCount(userId);
    }

    @GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSettings(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtUtil.getUserIdFromHeader(authorizationHeader);
        return client.feignGetSettings(userId);
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateSettings(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody @Valid NotificationSettingsDto notificationSettingsDto) {
        Long userId = jwtUtil.getUserIdFromHeader(authorizationHeader);
        return client.feignUpdateSettings(notificationSettingsDto, userId);
    }
}
