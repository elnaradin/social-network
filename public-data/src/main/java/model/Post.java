package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    private int id;

    private boolean isDeteled;

    private LocalDateTime time;

    private int authorId;

    private String title;

    private PostType type;

    private String postText;

    private boolean isBlocked;

    private int commentsCount;

    @OneToMany (mappedBy = "Post")
    private List<Tag> tags;

    private int likeAmount;

    private boolean myLike;

    private String imagePath;

    private boolean publishDate;

}
