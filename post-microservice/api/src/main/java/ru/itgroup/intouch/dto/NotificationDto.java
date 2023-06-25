package ru.itgroup.intouch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private Long authorId;
    private String notificationType;
    private Long entityId;
}
