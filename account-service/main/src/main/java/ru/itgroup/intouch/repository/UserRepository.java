package ru.itgroup.intouch.repository;


import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByHash(String hashCode);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.hash = null where u.hashExpiryTime < ?1")
    void clearHashAfterExpiry(LocalDateTime now);
}
