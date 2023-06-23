package ru.itgroup.intouch.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageDto extends WebSocketDto {
    private String notificationType;
    private Long authorId;
    private Long receiverId;
}

