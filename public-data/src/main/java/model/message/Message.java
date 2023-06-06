package model.message;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.MessageStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    private Instant time;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "recipient_id")
    private Long recipientId;
    @Column(name = "message_text")
    private String messageText;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id", referencedColumnName = "id")
    private Dialog dialog;

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public Long getDialogId() {
        return dialog.getId();
    }
}
