package ru.itgroup.intouch.repository;


import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByHashcode(String hashCode);
}
