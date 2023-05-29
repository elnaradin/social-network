package ru.itgroup.intouch.shedulers;

import lombok.RequiredArgsConstructor;
import model.Notification;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.BirthdayUsersDto;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.repository.NotificationRepository;
import ru.itgroup.intouch.repository.jooq.NotificationJooqRepository;
import ru.itgroup.intouch.service.notification.sender.NotificationSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BirthdayChecker {
    private final NotificationJooqRepository notificationJooqRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;
    private final MessageSource messageSource;

    private final LocalDateTime now = LocalDateTime.now();

    @Scheduled(cron = "0 30 0 * * *", zone = "Europe/Moscow")
    private void checkBirthDates() {
        List<BirthdayUsersDto> birthdayNotificationDtoList = notificationJooqRepository.getBirthdayUsers();
        if (birthdayNotificationDtoList.isEmpty()) {
            return;
        }

        List<NotificationDto> notificationDtoList = getNotificationDtoList(birthdayNotificationDtoList);
        List<Long> notificationIdList = notificationJooqRepository.saveNotifications(notificationDtoList);
        if (notificationIdList.isEmpty()) {
            return;
        }

        List<Notification> notifications = notificationRepository.findAllById(notificationIdList);
        notificationSender.send(notifications);
    }

    @NotNull
    private List<NotificationDto> getNotificationDtoList(@NotNull List<BirthdayUsersDto> birthdayNotificationDtoList) {
        return birthdayNotificationDtoList.stream().map(this::buildNotificationDto).toList();
    }

    private NotificationDto buildNotificationDto(BirthdayUsersDto dto) {
        return NotificationDto.builder()
                              .content(getMessage(dto))
                              .sentTime(now)
                              .notificationType(NotificationType.FRIEND_BIRTHDAY.name())
                              .authorId(dto.getAuthorId())
                              .receiverId(dto.getReceiverId())
                              .build();
    }

    private @NotNull String getMessage(@NotNull BirthdayUsersDto dto) {
        return messageSource.getMessage("notification.birthday", new Object[]{dto.getName(), dto.getLastName()},
                                        Locale.getDefault());
    }
}
