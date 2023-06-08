package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import model.enums.LikeType;

import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "time")
    private LocalDateTime time;
    @Column( name = "author_id")
    private long authorId;
    @Column(name = "item_id")
    private long itemId;
    @Column (name = "like_type")
    @Enumerated(EnumType.STRING)
    private LikeType type;
}