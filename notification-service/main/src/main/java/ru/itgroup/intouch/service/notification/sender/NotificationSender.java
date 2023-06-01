package ru.itgroup.intouch.service.notification.sender;

import model.Notification;
import ru.itgroup.intouch.contracts.service.notification.sender.NotificationObserver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationSender {
    private final Map<String, NotificationObserver> observers = new ConcurrentHashMap<>();

    public void subscribe(String className, NotificationObserver observer) {
        if (!observers.containsKey(className)) {
            observers.put(className, observer);
        }
    }

    public void unsubscribe(String className) {
        observers.remove(className);
    }

    public void send(Notification notification) {
        observers.forEach((key, observer) -> observer.send(notification));
    }

    public void send(List<Notification> notifications) {
        observers.forEach((key, observer) -> observer.send(notifications));
    }
}
