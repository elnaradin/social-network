package ru.itgroup.intouch.dto.dialog;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.message.LastMessageDTO;

@Data
@NoArgsConstructor
public class DialogDTO {
    private Long id;
    private boolean isDeleted;
    private Integer unreadCount;
    private ConversationPartner conversationPartner;
    private LastMessageDTO lastMessage;

}
