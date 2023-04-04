package ru.itgroup.intouch.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post_type")
public class PostTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String code;
    private String name;

}
