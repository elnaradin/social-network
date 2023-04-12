package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

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
