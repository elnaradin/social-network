package ru.itgroup.intouch.repository;

import model.message.AccountDialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountDialogRepository extends JpaRepository<AccountDialog, Long> {

    Optional<AccountDialog> findByAccountIdAndRecipientId(Long accountId, Long recipientId);

    @Query(value = "select ac from AccountDialog ac where ac.accountId = :accountId " +
            "and ac.dialog.isDeleted = false order by ac.dialog.lastMessage.time desc")
    Page<AccountDialog> getDialogsPageByAccountId(Integer accountId, Pageable pageable);
}
