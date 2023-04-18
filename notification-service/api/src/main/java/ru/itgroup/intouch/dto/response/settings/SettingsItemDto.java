package ru.itgroup.intouch.dto.response.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsItemDto {
    private boolean enable;
    private String notificationType;
}
