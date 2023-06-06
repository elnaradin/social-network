package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Notification;
import model.account.Account;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.mapper.NotificationListMapper;
import ru.itgroup.intouch.repository.AccountRepository;
import ru.itgroup.intouch.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    private final NotificationListMapper notificationListMapper;

    public NotificationListDto getNotifications() {
        Account receiver = accountRepository.findById(1);
        List<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(receiver);
        readNotifications(notifications);

        return notificationListMapper.getDestination(notifications);
    }

    public NotificationCountDto countNewNotifications() {
        Account receiver = accountRepository.findById(1);
        long notificationsCount = notificationRepository.countByReceiverAndReadAtIsNull(receiver);

        return NotificationCountDto.builder().data(new CountDto(notificationsCount)).build();
    }

    private void readNotifications(@NotNull List<Notification> notifications) {
        if (notifications.isEmpty()) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        notifications.forEach(notification -> notification.setReadAt(now));
        notificationRepository.saveAll(notifications);
    }
}
