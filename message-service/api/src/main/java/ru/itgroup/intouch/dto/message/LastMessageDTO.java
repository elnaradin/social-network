package ru.itgroup.intouch.dto.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LastMessageDTO {
    private Long id;
    private boolean isDeleted;
    private Long time;
    private Long authorId;
    private Long recipientId;
    private String messageText;
    private String readStatusDto;
    private Long dialogId;
}
