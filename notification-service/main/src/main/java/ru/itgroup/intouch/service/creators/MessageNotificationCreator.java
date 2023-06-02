package ru.itgroup.intouch.service.creators;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

import static ru.itgroup.intouch.Tables.MESSAGES;
import static ru.itgroup.intouch.Tables.NOTIFICATION_SETTINGS;

@Service
@RequiredArgsConstructor
public class MessageNotificationCreator extends AbstractNotificationCreator implements NotificationCreator {
    private static final TableField<NotificationSettingsRecord, Boolean> TABLE_FIELD = NOTIFICATION_SETTINGS.MESSAGE;

    private final DSLContext dsl;

    public TableField<NotificationSettingsRecord, Boolean> getTableField() {
        return TABLE_FIELD;
    }

    public String getContent(Long entityId) {
        return dsl.select(MESSAGES.MESSAGE_TEXT)
                  .from(MESSAGES)
                  .where(MESSAGES.ID.eq(entityId))
                  .fetchOptional()
                  .map(record -> record.getValue(MESSAGES.MESSAGE_TEXT))
                  .orElse("");
    }
}
