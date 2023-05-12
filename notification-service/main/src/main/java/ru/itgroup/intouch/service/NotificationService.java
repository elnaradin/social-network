package ru.itgroup.intouch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import model.Notification;
import model.account.Account;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.mapper.NotificationListMapper;
import ru.itgroup.intouch.mapper.NotificationMapper;
import ru.itgroup.intouch.repository.AccountRepository;
import ru.itgroup.intouch.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    private final NotificationListMapper notificationListMapper;
    private final NotificationMapper notificationMapper;
    private final ObjectMapper objectMapper;

    public NotificationListDto getNotifications() {
        Account receiver = accountRepository.findById(2);
        List<Notification> notifications = notificationRepository.findByReceiverOrderByCreatedAtDesc(receiver);

        return notificationListMapper.getDestination(notifications);
    }

    public NotificationCountDto countNewNotifications() {
        Account receiver = accountRepository.findById(1);
        long notificationsCount = notificationRepository.countByReceiverAndReadAtIsNull(receiver);

        return NotificationCountDto.builder().data(new CountDto(notificationsCount)).build();
    }

    public TextMessage getRandomNotification() throws JsonProcessingException {
        Notification notification = notificationRepository.findRandom();
        NotificationDto notificationDto = notificationMapper.getDestination(notification);
        String json = objectMapper.writeValueAsString(notificationDto);

        return new TextMessage(json);
    }
}
