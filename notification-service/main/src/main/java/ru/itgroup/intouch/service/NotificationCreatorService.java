package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Notification;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.repository.NotificationRepository;
import ru.itgroup.intouch.repository.jooq.FriendRepository;
import ru.itgroup.intouch.repository.jooq.NotificationJooqRepository;
import ru.itgroup.intouch.repository.jooq.NotificationSettingRepository;
import ru.itgroup.intouch.service.notification.sender.NotificationSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotificationCreatorService {
    private static final int CONTENT_LENGTH = 120;

    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationJooqRepository notificationJooqRepository;
    private final NotificationCreatorFactory notificationCreatorFactory;
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;
    private final FriendRepository friendRepository;

    private NotificationCreator notificationCreator;

    public void createNotification(@NotNull NotificationRequestDto notificationRequestDto)
            throws ClassNotFoundException {
        notificationCreator = notificationCreatorFactory
                .getNotificationCreator(notificationRequestDto.getNotificationType());

        notificationCreator.validateData(notificationRequestDto);
        String content = getContent(notificationRequestDto.getEntityId());
        if (notificationRequestDto.getReceiverId() == null) {
            createMassNotifications(notificationRequestDto, content);
            return;
        }

        createSingleNotification(notificationRequestDto, content);
    }

    private @NotNull String getContent(Long entityId) {
        String content = notificationCreator.getContent(entityId);
        if (content.length() <= CONTENT_LENGTH) {
            return content;
        }

        return content.substring(0, CONTENT_LENGTH - 3).trim() + "...";
    }

    private void createMassNotifications(@NotNull NotificationRequestDto notificationRequestDto, String content) {
        Set<Long> receiverIdList = new HashSet<>(
                friendRepository
                        .getReceiverIds(notificationRequestDto.getAuthorId(), notificationCreator.getTableField())
        );

        if (receiverIdList.isEmpty()) {
            return;
        }

        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for (Long receiverId : receiverIdList) {
            notificationRequestDto.setReceiverId(receiverId);
            notificationDtoList.add(notificationCreator.create(notificationRequestDto, content));
        }

        List<Long> notificationIdList = notificationJooqRepository.saveNotifications(notificationDtoList);
        List<Notification> notifications = notificationRepository.findAllById(notificationIdList);
        notificationSender.send(notifications);
    }

    private void createSingleNotification(@NotNull NotificationRequestDto notificationRequestDto, String content) {
        boolean isEnableNotification = notificationSettingRepository
                .isEnable(notificationRequestDto.getReceiverId(), notificationCreator.getTableField());
        if (!isEnableNotification) {
            return;
        }

        NotificationDto notificationDto = notificationCreator.create(notificationRequestDto, content);
        long notificationId = notificationJooqRepository.saveNotificationFromDto(notificationDto);

        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification == null) {
            return;
        }

        notificationSender.send(notification);
    }
}
