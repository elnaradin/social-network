package ru.itgroup.intouch.repository.jooq;

import lombok.RequiredArgsConstructor;
import model.enums.Status;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.annotation.Loggable;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

import java.util.List;

import static ru.itgroup.intouch.Tables.FRIENDS;
import static ru.itgroup.intouch.Tables.NOTIFICATION_SETTINGS;

@Repository
@RequiredArgsConstructor
public class FriendRepository {
    private final DSLContext dsl;

    @Loggable
    public List<Long> getReceiverIds(Long userId, @NotNull TableField<NotificationSettingsRecord, Boolean> field) {
        Condition condition = field.isTrue().or(field.isNull());

        return dsl.select(FRIENDS.USER_ID_FROM)
                  .from(FRIENDS)
                  .leftJoin(NOTIFICATION_SETTINGS)
                  .on(FRIENDS.USER_ID_FROM.eq(NOTIFICATION_SETTINGS.USER_ID))
                  .where(FRIENDS.USER_ID_TO.eq(userId)
                                           .and(FRIENDS.STATUS_CODE.eq(Status.FRIEND.getStatus()))
                                           .and(condition))
                  .fetch()
                  .getValues(FRIENDS.USER_ID_FROM);
    }
}
