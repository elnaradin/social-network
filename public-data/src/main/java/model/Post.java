package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.PostType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "publish_date")
    private LocalDateTime publishDate;
    @Column(name = "time_changed")
    private LocalDateTime timeChanged;
    @Column(name = "author_id")
    private Integer authorId; //TODO: connection to PersonaEntity
    private PostType type;
    @Column(name = "post_text", columnDefinition = "TEXT")
    private String postText;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post2tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "my_like")
    private boolean myLike;
    @Column(name = "image_path")
    private String imagePath; //TODO: connection to StorageService

}
