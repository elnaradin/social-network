package ru.itgroup.intouch.repository;


import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByHash(String hashCode);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.hash = null where u.hashExpiryTime < ?1")
    void clearHashAfterExpiry(LocalDateTime now);

    @Query(value = "SELECT id FROM users WHERE last_name IN (:names) OR first_name IN (:names)", nativeQuery = true)
    List<Long> findAllByNames(@Param("names") String[] names);
}
