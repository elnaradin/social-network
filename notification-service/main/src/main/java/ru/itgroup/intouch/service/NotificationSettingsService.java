package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.NotificationSettings;
import model.account.Account;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;
import ru.itgroup.intouch.dto.response.settings.SettingsItemDto;
import ru.itgroup.intouch.mapper.NotificationSettingsMapper;
import ru.itgroup.intouch.repository.AccountRepository;
import ru.itgroup.intouch.repository.NotificationSettingsRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSettingsService {
    private final NotificationSettingsRepository notificationSettingsRepository;
    private final AccountRepository accountRepository;
    private final NotificationSettingsMapper notificationSettingsMapper;

    public List<SettingsItemDto> getSettings()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        NotificationSettings notificationSettings = getNotificationSettingsModel();
        return notificationSettingsMapper.getDestination(notificationSettings);
    }

    public void updateSettings(@NotNull NotificationSettingsDto notificationSettingsDto) {
        NotificationSettings notificationSettings = getNotificationSettingsModel();
        NotificationType notificationType = NotificationType.valueOf(notificationSettingsDto.getNotificationType());
        switch (notificationType) {
            case POST -> notificationSettings.setPost(notificationSettingsDto.isEnable());
            case POST_COMMENT -> notificationSettings.setPostComment(notificationSettingsDto.isEnable());
            case COMMENT_COMMENT -> notificationSettings.setCommentComment(notificationSettingsDto.isEnable());
            case MESSAGE -> notificationSettings.setMessage(notificationSettingsDto.isEnable());
            case FRIEND_REQUEST -> notificationSettings.setFriendRequest(notificationSettingsDto.isEnable());
            case FRIEND_BIRTHDAY -> notificationSettings.setFriendBirthday(notificationSettingsDto.isEnable());
        }

        notificationSettingsRepository.save(notificationSettings);
    }

    private @NotNull NotificationSettings getNotificationSettingsModel() {
        Account account = accountRepository.findById(1);
        NotificationSettings notificationSettings = notificationSettingsRepository.findByUser(account);
        if (notificationSettings == null) {
            notificationSettings = new NotificationSettings();
            notificationSettings.setUser(account);
            notificationSettingsRepository.save(notificationSettings);
        }

        return notificationSettings;
    }
}
