package ru.itgroup.intouch.contracts.service.notification.sender;

import model.Notification;

import java.util.List;

public interface NotificationObserver {
    void send(Notification notification);

    void send(List<Notification> notifications);
}
