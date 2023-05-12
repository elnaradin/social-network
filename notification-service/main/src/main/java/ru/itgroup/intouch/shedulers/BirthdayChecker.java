package ru.itgroup.intouch.shedulers;

import lombok.RequiredArgsConstructor;
import model.Notification;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.controller.NotificationHandler;
import ru.itgroup.intouch.dto.BirthdayUsersDto;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.mapper.NotificationMapper;
import ru.itgroup.intouch.repository.NotificationJdbcRepository;
import ru.itgroup.intouch.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BirthdayChecker {
    private final NotificationJdbcRepository notificationJdbcRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationHandler notificationHandler;
    private final NotificationMapper notificationMapper;
    private final MessageSource messageSource;

    private final LocalDateTime now = LocalDateTime.now();

    @Scheduled(cron = "0 30 0 * * *", zone = "Europe/Moscow")
    private void checkBirthDates() {
        List<BirthdayUsersDto> birthdayNotificationDtoList = notificationJdbcRepository.getBirthdayUsers();
        if (birthdayNotificationDtoList.isEmpty()) {
            return;
        }

        List<NotificationDto> notificationDtoList = getNotificationDtoList(birthdayNotificationDtoList);
        notificationJdbcRepository.saveBirthdayNotifications(notificationDtoList);
        sendNotifications();
    }

    @NotNull
    private List<NotificationDto> getNotificationDtoList(@NotNull List<BirthdayUsersDto> birthdayNotificationDtoList) {
        return birthdayNotificationDtoList.stream()
                .map(dto -> NotificationDto.builder()
                        .content(getMessage(dto))
                        .sentTime(now)
                        .notificationType(NotificationType.FRIEND_BIRTHDAY.name())
                        .authorId(dto.getAuthorId())
                        .receiverId(dto.getReceiverId())
                        .build())
                .toList();
    }

    private @NotNull String getMessage(@NotNull BirthdayUsersDto dto) {
        return messageSource.getMessage("notification.birthday", new Object[]{dto.getName(), dto.getLastName()},
                                        Locale.getDefault()
        );
    }

    private void sendNotifications() {
        List<Notification> notifications = notificationRepository
                .findNotificationsByNotificationTypeAndCreatedAt(NotificationType.FRIEND_BIRTHDAY.name(), now);

        for (Notification notification : notifications) {
            notificationHandler.sendNotification(notificationMapper.getDestination(notification));
        }
    }
}
