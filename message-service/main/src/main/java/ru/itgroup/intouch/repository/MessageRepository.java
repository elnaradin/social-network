package ru.itgroup.intouch.repository;

import model.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value =
            "select count(id) from messages mes where dialog_id in " +
            "(select id from dialogs d where id in " +
            "(select dialog_id from account_dialogs where account_id = :accountId) and " +
            "last_message_id = (select id from messages last_mes where last_mes.id = d.last_message_id and " +
            "last_mes.recipient_id = :accountId and last_mes.status != 'READ')) and " +
            "mes.recipient_id = :accountId and mes.status != 'READ'", nativeQuery = true)
    Integer getUnreadMessageCountByAccountId(Integer accountId);

    @Query(value = "select * from messages where dialog_id = :dialogId", nativeQuery = true)
    Page<Message> findAllByDialogId(Long dialogId, Pageable pageable);

    @Modifying
    @Query(value = "update messages set status = 'READ' where dialog_id = :dialogId and recipient_id = :userId", nativeQuery = true)
    void updateMessageStatus(Long dialogId, Long userId);
}
