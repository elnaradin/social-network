package ru.itgroup.intouch.repository;

import model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findFirstByEmailEqualsAndIsDeletedEquals(String email, boolean isDeleted);
    List<Account> findByIdIn(List<Long> userIds);
}
