package ru.itgroup.intouch.service.creators;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;

import java.time.LocalDateTime;
import java.util.Locale;

@AllArgsConstructor
@RequiredArgsConstructor
abstract class AbstractNotificationCreator {
    private MessageSource messageSource;

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
        return messageSource.getMessage(name, null, Locale.getDefault());
    }
}
