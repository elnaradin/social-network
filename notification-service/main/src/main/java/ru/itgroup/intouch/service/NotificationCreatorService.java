package ru.itgroup.intouch.service;

import model.Notification;
import model.User;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.repository.NotificationRepository;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationCreatorService {
    private User receiver;
    private User author;

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationCreatorService(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(NotificationRequestDto notificationRequestDto) {
        setUserProperties(notificationRequestDto);
        Notification notification = buildNotification(notificationRequestDto);
        notificationRepository.save(notification);
    }

    private void setUserProperties(NotificationRequestDto notificationRequestDto) {
        List<User> users = getUsers(notificationRequestDto);
        for (User user : users) {
            if (user.getId().equals(notificationRequestDto.getReceiverId())) {
                receiver = user;
                continue;
            }

            author = user;
        }
    }

    private @NotNull List<User> getUsers(@NotNull NotificationRequestDto notificationRequestDto) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(notificationRequestDto.getAuthorId());
        userIds.add(notificationRequestDto.getReceiverId());

        return userRepository.findAllById(userIds);
    }

    private Notification buildNotification(@NotNull NotificationRequestDto notificationRequestDto) {
        return Notification.builder()
                .content(notificationRequestDto.getContent())
                .notificationType(NotificationType.valueOf(notificationRequestDto.getNotificationType()).name())
                .author(author)
                .receiver(receiver)
                .build();
    }
}
