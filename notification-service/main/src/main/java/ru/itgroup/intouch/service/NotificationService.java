package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Notification;
import model.account.User;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.mapper.NotificationListMapper;
import ru.itgroup.intouch.repository.NotificationRepository;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationListMapper notificationListMapper;

    public NotificationListDto getNotifications() {
        User receiver = userRepository.findById(1);
        List<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(receiver);

        return notificationListMapper.getDestination(notifications);
    }

    public NotificationCountDto countNewNotifications() {
        User receiver = userRepository.findById(1);
        long notificationsCount = notificationRepository.countByReceiverAndReadAtIsNull(receiver);

        return NotificationCountDto.builder().data(new CountDto(notificationsCount)).build();
    }
}
