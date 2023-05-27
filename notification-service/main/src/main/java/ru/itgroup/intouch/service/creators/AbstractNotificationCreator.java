package ru.itgroup.intouch.service.creators;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;

import java.time.LocalDateTime;
import java.util.Locale;

@AllArgsConstructor
abstract class AbstractNotificationCreator {
    private final MessageSource messageSource;

    public NotificationDto create(@NotNull NotificationRequestDto notificationRequestDto) {
        return NotificationDto
                .builder()
                .content(getContent())
                .notificationType(notificationRequestDto.getNotificationType())
                .authorId(notificationRequestDto.getAuthorId())
                .receiverId(notificationRequestDto.getReceiverId())
                .sentTime(LocalDateTime.now())
                .build();
    }

    protected abstract String getContent();

    protected String getMessage(String name) {
        System.out.println(messageSource);
        return messageSource.getMessage(name, null, Locale.getDefault());
    }
}
