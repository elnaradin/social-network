package ru.itgroup.intouch.repository;

import model.Post;
import model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT id FROM users WHERE last_name IN (:names) OR first_name IN (:names)", nativeQuery = true)
    List<Long> findAllIdByNames(@Param("names") String[] names);

    Optional<User> findByEmail (@Param("email") String email);
}
