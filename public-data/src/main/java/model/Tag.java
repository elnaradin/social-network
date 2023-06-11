package model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


//@Builder

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post_tags")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "postTags")

    private Set<Post> posts = new HashSet<Post>();

    public Tag () {
        super();

    }

   }
