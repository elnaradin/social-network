package ru.itgroup.intouch.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Table
@Entity(name = "posts")
public class PostEntity {
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
    @ManyToOne
    @JoinColumn(name = "type_id")
    private PostTypeEntity type;
    @Column(name = "post_text", columnDefinition = "TEXT")
    private String postText;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post2tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<PostTagEntity> tags;
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "my_like")
    private boolean myLike;
    @Column(name = "image_path")
    private String imagePath; //TODO: connection to StorageService


}
