package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import model.enums.CommentType;

import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "time_changed")
    private LocalDateTime timeChanged;
    @Column(name = "author_id")
    private Long authorId; //TODO: connection to PersonaEntity
    @Column(name= "parent_id")
    private Long parentId;
    @Column(name = "comment_type")
    @Enumerated(EnumType.STRING)
    private CommentType commentType;
    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;
    @Column(name = "post_id")
    private Long postId;
    @Column(name = "is_blocked")
    private boolean isBlocked;
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "my_like")
    private boolean myLike;
    @Column(name = "comments_count")
    private Integer commentsCount;
    @Column(name = "image_path")
    private String imagePath; //TODO: connection to StorageService
}
