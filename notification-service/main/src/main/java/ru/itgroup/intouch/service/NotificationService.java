package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Notification;
import model.User;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.ResponseDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.mapper.NotificationListMapper;
import ru.itgroup.intouch.repository.NotificationRepository;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationListMapper notificationListMapper;

    public ResponseDto getNotifications() {
        User receiver = userRepository.findById(1);
        List<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(receiver);

        return notificationListMapper.getDestination(notifications);
    }

    public ResponseDto countNewNotifications() {
        User receiver = userRepository.findById(new Random().nextInt(1 + 50) + 1);
        long notificationsCount = notificationRepository.countByReceiverAndReadAtIsNull(receiver);

        return NotificationCountDto.builder().data(new CountDto(notificationsCount)).build();
    }
}
