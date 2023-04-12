package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {
    @Id
    private int id;

    private boolean isDeleted;

    private LocalDateTime time;

    private int authorId;

    private int recipientId;

    private String messageText;

    private MessageStatus readStatusDto;

    private int dialogId;


    public Message(int id, boolean isDeleted, LocalDateTime time, int authorId, String messageText) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.time = time;
        this.authorId = authorId;
        this.messageText = messageText;
    }
}
