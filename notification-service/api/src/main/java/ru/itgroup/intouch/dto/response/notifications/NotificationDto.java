package ru.itgroup.intouch.dto.response.notifications;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    @JMap
    private Long id;

    private AuthorDto author;

    @JMap
    private String content;

    @JMap
    private NotificationType notificationType;

    @JMap("createdAt")
    private LocalDateTime timestamp;

    private Long receiverId;
}
