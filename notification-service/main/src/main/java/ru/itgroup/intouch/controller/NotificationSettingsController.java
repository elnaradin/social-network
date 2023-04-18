package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;
import ru.itgroup.intouch.dto.response.settings.SettingsItemDto;
import ru.itgroup.intouch.service.NotificationSettingsService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationSettingsController {
    private final NotificationSettingsService notificationSettingsService;

    @GetMapping("/settings")
    public List<SettingsItemDto> getSettings()
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return notificationSettingsService.getSettings();
    }

    @PutMapping("/settings")
    public ResponseEntity<?> updateSettings(@RequestBody NotificationSettingsDto notificationSettingsDto) {
        notificationSettingsService.updateSettings(notificationSettingsDto);
        return ResponseEntity.ok("");
    }
}
