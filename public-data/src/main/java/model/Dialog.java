package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Dialog {

    @Id
    private int id;

    private boolean isDeleted;

    private int unreadCount;

    @ManyToOne
    @JoinColumn(name = "conversation_partner_id")
    private Account conversationPartner;

    private Message lastMessage;
}
