package ru.itgroup.intouch.repository.jooq;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.InsertValuesStep5;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.dto.BirthdayUsersDto;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.mapper.BirthdayUsersMapper;
import ru.itgroup.intouch.tables.records.NotificationsRecord;

import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.currentDate;
import static org.jooq.impl.DSL.extract;
import static ru.itgroup.intouch.Tables.ACCOUNTS;
import static ru.itgroup.intouch.Tables.FRIENDS;
import static ru.itgroup.intouch.Tables.NOTIFICATIONS;
import static ru.itgroup.intouch.Tables.USERS;

@Repository
@RequiredArgsConstructor
public class NotificationJooqRepository {
    private final DSLContext dsl;
    private final BirthdayUsersMapper birthdayUsersMapper;

    public List<BirthdayUsersDto> getBirthdayUsers() {
        return dsl.select(FRIENDS.USER_ID_FROM, FRIENDS.USER_ID_TO, USERS.FIRST_NAME, USERS.LAST_NAME)
                  .from(ACCOUNTS)
                  .leftJoin(FRIENDS)
                  .on(ACCOUNTS.ID.eq(FRIENDS.USER_ID_TO))
                  .leftJoin(USERS)
                  .on(ACCOUNTS.ID.eq(USERS.ID))
                  .where(extract(ACCOUNTS.BIRTH_DATE, DatePart.MONTH).eq(extract(currentDate(), DatePart.MONTH)))
                  .and(extract(ACCOUNTS.BIRTH_DATE, DatePart.DAY).eq(extract(currentDate(), DatePart.DAY)))
                  .and(FRIENDS.ID.isNotNull())
                  .fetch()
                  .map(birthdayUsersMapper);
    }

    public List<Long> saveNotifications(@NotNull List<NotificationDto> notificationDtoList) {
        if (notificationDtoList.isEmpty()) {
            return null;
        }

        InsertValuesStep5<NotificationsRecord, String, LocalDateTime, String, Long, Long> insertStep =
                dsl.insertInto(NOTIFICATIONS)
                   .columns(NOTIFICATIONS.CONTENT,
                            NOTIFICATIONS.CREATED_AT,
                            NOTIFICATIONS.NOTIFICATION_TYPE,
                            NOTIFICATIONS.AUTHOR_ID,
                            NOTIFICATIONS.RECEIVER_ID);

        for (NotificationDto dto : notificationDtoList) {
            insertStep = insertStep.values(dto.getContent(),
                                           dto.getSentTime(),
                                           dto.getNotificationType(),
                                           dto.getAuthorId(),
                                           dto.getReceiverId());
        }

        return insertStep.returning(NOTIFICATIONS.ID).fetch().getValues(NOTIFICATIONS.ID);
    }

    public Long saveNotificationFromDto(@NotNull NotificationDto dto) {
        return dsl.insertInto(NOTIFICATIONS,
                              NOTIFICATIONS.CONTENT,
                              NOTIFICATIONS.CREATED_AT,
                              NOTIFICATIONS.NOTIFICATION_TYPE,
                              NOTIFICATIONS.AUTHOR_ID,
                              NOTIFICATIONS.RECEIVER_ID)
                  .values(dto.getContent(),
                          dto.getSentTime(),
                          dto.getNotificationType(),
                          dto.getAuthorId(),
                          dto.getReceiverId())
                  .returning(NOTIFICATIONS.ID)
                  .fetchOptional()
                  .map(record -> record.getValue(NOTIFICATIONS.ID))
                  .orElse(null);
    }
}
