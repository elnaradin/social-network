package ru.itgroup.intouch.repository;

import model.Notification;
import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverOrderByCreatedAtDesc(User receiver);

    long countByReceiverAndReadAtIsNull(User receiver);

    @Query("FROM Notification ORDER BY RANDOM() LIMIT 1")
    Notification findRandom();

    List<Notification> findNotificationsByNotificationTypeAndCreatedAt(
            String notificationType, LocalDateTime createdAt
    );
}
