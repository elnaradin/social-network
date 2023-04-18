package ru.itgroup.intouch.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import model.enums.NotificationType;

@Data
public class NotificationSettingsDto {
    private boolean enable;

    @JsonProperty("notification_type")
    private NotificationType notificationType;
}
