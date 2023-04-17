package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;



@Data
@Entity
public class User {
    @Id

    private int id;

    private boolean isDeleted;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
