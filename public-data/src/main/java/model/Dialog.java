package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dialog {
    @Id
    private Long id;

    private boolean isDeleted;

    private int unreadCount;

    @ManyToOne
    @JoinColumn(name = "conversation_partner_id")
    private Account conversationPartner;

    @ManyToOne
    private Message lastMessage;
}
