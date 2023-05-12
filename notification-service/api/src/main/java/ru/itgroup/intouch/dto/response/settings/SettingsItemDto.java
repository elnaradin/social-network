package ru.itgroup.intouch.dto.response.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsItemDto {
    private boolean enable;

    @JsonProperty("notification_type")
    private String notificationType;
}
