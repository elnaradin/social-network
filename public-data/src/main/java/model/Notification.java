package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import model.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;

    private String content;

    private NotificationType notificationType;

    private LocalDateTime sentTime;
}
