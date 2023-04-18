package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.LikeType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Like {
    @Id
    private Long id;

    private boolean isDeleted;

    private int authorId;

    private LocalDateTime time;

    private int itemId;

    private LikeType type;
}
