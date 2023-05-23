package ru.itgroup.intouch.service.creators;

import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

import static ru.itgroup.intouch.Tables.NOTIFICATION_SETTINGS;

@Service
public class MessageNotificationCreator extends AbstractNotificationCreator implements NotificationCreator {
    private static final TableField<NotificationSettingsRecord, Boolean> TABLE_FIELD = NOTIFICATION_SETTINGS.MESSAGE;

    @Autowired
    public MessageNotificationCreator(MessageSource messageSource) {
        super(messageSource);
    }

    public TableField<NotificationSettingsRecord, Boolean> getTableField() {
        return TABLE_FIELD;
    }

    // TODO: Заменить эту заглушку
    protected String getContent() {
        return getMessage("notification.message");
    }
}
