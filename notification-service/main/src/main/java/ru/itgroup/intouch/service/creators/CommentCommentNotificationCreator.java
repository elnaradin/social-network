package ru.itgroup.intouch.service.creators;

import org.jooq.TableField;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

import static ru.itgroup.intouch.Tables.NOTIFICATION_SETTINGS;

@Service
public class CommentCommentNotificationCreator extends AbstractNotificationCreator implements NotificationCreator {
    private static final TableField<NotificationSettingsRecord, Boolean> TABLE_FIELD =
            NOTIFICATION_SETTINGS.COMMENT_COMMENT;

    @Override
    public TableField<NotificationSettingsRecord, Boolean> getTableField() {
        return TABLE_FIELD;
    }

    // TODO: Заменить эту заглушку
    protected String getContent() {
        return getMessage("notification.comment_comment");
    }
}
