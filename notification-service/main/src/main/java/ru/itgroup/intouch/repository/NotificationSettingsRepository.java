package ru.itgroup.intouch.repository;

import model.NotificationSettings;
import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {
    NotificationSettings findByUser(User user);
}
