package ru.itgroup.intouch.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import model.enums.NotificationType;
import ru.itgroup.intouch.annotations.ValidEnum;

@Data
public class NotificationSettingsDto {
    @NotNull(message = "Enable can't be null")
    private boolean enable;

    @JsonProperty("notification_type")
    @NotNull(message = "Notification type can't be null")
    @NotBlank(message = "Value can't be empty")
    @ValidEnum(enumClass = NotificationType.class)
    private String notificationType;
}
