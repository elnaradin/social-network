package model.post;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post_type")
public class PostType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String code;
    private String name;
}
