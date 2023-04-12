package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Like {
    @Id
    private int id;

    private boolean isDeleted;

    private int authorId;

    private LocalDateTime time;

    private int itemId;

    private LikeType type;
}
