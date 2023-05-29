package ru.itgroup.intouch.contracts.service.creators;

import org.jetbrains.annotations.NotNull;
import org.jooq.TableField;
import ru.itgroup.intouch.dto.NotificationDto;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.tables.records.NotificationSettingsRecord;

public interface NotificationCreator {
    NotificationDto create(NotificationRequestDto notificationRequestDto, String content);

    TableField<NotificationSettingsRecord, Boolean> getTableField();

    String getContent(Long entityId);

    void validateData(@NotNull NotificationRequestDto notificationRequestDto);
}
