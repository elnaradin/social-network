package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Image(String name, String URL) {
        this.photoName = name;
        this.photoPath = URL;
    }
}
