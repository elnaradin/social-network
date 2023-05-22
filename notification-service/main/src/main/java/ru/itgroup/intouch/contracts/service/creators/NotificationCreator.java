package ru.itgroup.intouch.contracts.service.creators;

import org.jooq.TableField;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

public interface NotificationCreator {
    NotificationDto create(NotificationRequestDto notificationRequestDto) throws Exception;

    TableField<NotificationSettingsRecord, Boolean> getTableField();
}
