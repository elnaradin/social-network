package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import model.enums.LikeType;

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
