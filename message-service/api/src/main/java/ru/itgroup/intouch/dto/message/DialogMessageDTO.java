package ru.itgroup.intouch.dto.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DialogMessageDTO {
    private Long id;
    private boolean isDeleted;
    private Long time;
    private Long authorId;
    private String messageText;
}
