package ru.itgroup.intouch.repository;

import model.message.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    @Query(value = "select COUNT(id) from messages where dialog_id = :dialogId and recipient_id = :accountId and " +
            "status != 'READ'", nativeQuery = true)
    Integer getUnreadMessageCount(Long accountId, Long dialogId);

}
