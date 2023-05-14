package ru.itgroup.intouch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDto {
    private String content;
    private LocalDateTime sentTime;
    private String notificationType;
    private Long authorId;
    private Long receiverId;
}
