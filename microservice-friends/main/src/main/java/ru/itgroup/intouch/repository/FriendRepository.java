package ru.itgroup.intouch.repository;


import model.Friend;
import model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> getByUserIdFrom(Account account);
    Optional<Friend> getByUserIdTo(Account account);
    Optional<Friend> getByUserIdFromAndUserIdTo(Account accountFrom, Account accountTo);
    List<Friend> getAllByUserIdFrom(Account accountFrom);
    List<Friend> getAllByUserIdFromAndStatusCode(Account accountFrom, String status);
    List<Friend> getAllByUserIdToAndStatusCode(Account accountTo, String status);
    List<Friend> getAllByUserIdFromAndUserIdToIsNot(Account accountFrom, Account accountTo);
    List<Friend> getAllByUserIdTo(Account accountTo);
}
