package ru.itgroup.intouch.repository;

import model.Notification;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverOrderByCreatedAtDesc(User receiver);

    long countByReceiverAndReadAtIsNull(User receiver);
}
