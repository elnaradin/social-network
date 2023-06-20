package ru.itgroup.intouch.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itgroup.intouch.dto.message.DialogMessageDTO;
import ru.itgroup.intouch.dto.parents.WebSocketDto;

@Data
@AllArgsConstructor
public class WebSocketMessageDto extends WebSocketDto {

    private String type;
    private Long recipientId;
    private DialogMessageDTO data;
}

