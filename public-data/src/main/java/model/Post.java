package model;

import jakarta.persistence.*;
import lombok.*;
import model.enums.PostType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post implements Serializable {
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
    private Long authorId; //TODO: connection to PersonaEntity
    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Column(name = "post_text", columnDefinition = "TEXT")
    private String postText;
    @ManyToMany(cascade = CascadeType.ALL,   fetch = FetchType.LAZY)
    @JoinTable(
            name = "post2tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> postTags;
    @Column (name = "comments_count")
    private Integer commentsCount;
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "my_like")
    private boolean myLike;
    @Column(name = "image_path")
    private String imagePath; //TODO: connection to StorageService

}
