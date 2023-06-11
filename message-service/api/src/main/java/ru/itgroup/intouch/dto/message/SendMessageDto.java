package ru.itgroup.intouch.dto.message;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class SendMessageDto {

    private Long id;
    private Long authorId;
    private String messageText;
    private Long recipientId;
    private Long time;
}
