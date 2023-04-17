package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Tag {

    @Id
    int id;

    String tag;



}
