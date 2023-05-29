package ru.itgroup.intouch.service.creators;

import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.exception.InvalidRequestDataException;

import java.time.LocalDateTime;

abstract class AbstractNotificationCreator {
    public NotificationDto create(@NotNull NotificationRequestDto notificationRequestDto, String content) {
        return NotificationDto.builder()
                              .content(content)
                              .notificationType(notificationRequestDto.getNotificationType())
                              .authorId(notificationRequestDto.getAuthorId())
                              .receiverId(notificationRequestDto.getReceiverId())
                              .sentTime(LocalDateTime.now())
                              .build();
    }

    public void validateData(@NotNull NotificationRequestDto notificationRequestDto) {
        switch (NotificationType.valueOf(notificationRequestDto.getNotificationType())) {
            case FRIEND_REQUEST, MESSAGE -> {
                if (notificationRequestDto.getReceiverId() == null) {
                    throw new InvalidRequestDataException("Receiver id can't be null");
                }
            }
            default -> {
                if (notificationRequestDto.getEntityId() == null) {
                    throw new InvalidRequestDataException("Entity id can't be null");
                }
            }
        }
    }
}
