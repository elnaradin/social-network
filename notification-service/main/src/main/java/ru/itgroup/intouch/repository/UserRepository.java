package ru.itgroup.intouch.repository;

import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long userId);
}
