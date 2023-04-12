package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    private int id;

    private Account author;

    private String content;

    private NotificationType notificationType;

    private LocalDateTime sentTime;
}
