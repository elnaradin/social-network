package ru.itgroup.intouch.service;

import model.Notification;
import model.account.Account;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.repository.AccountRepository;
import ru.itgroup.intouch.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationCreatorService {
    private Account receiver;
    private Account author;

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationCreatorService(
            AccountRepository accountRepository, NotificationRepository notificationRepository
    ) {
        this.accountRepository = accountRepository;
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(NotificationRequestDto notificationRequestDto) {
        setUserProperties(notificationRequestDto);
        Notification notification = buildNotification(notificationRequestDto);
        notificationRepository.save(notification);
    }

    private void setUserProperties(NotificationRequestDto notificationRequestDto) {
        List<Account> users = getUsers(notificationRequestDto);
        for (Account user : users) {
            if (user.getId().equals(notificationRequestDto.getReceiverId())) {
                receiver = user;
                continue;
            }

            author = user;
        }
    }

    private @NotNull List<Account> getUsers(@NotNull NotificationRequestDto notificationRequestDto) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(notificationRequestDto.getAuthorId());
        userIds.add(notificationRequestDto.getReceiverId());

        return accountRepository.findAllById(userIds);
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
