package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import model.enums.Status;

import java.time.LocalDateTime;

@Data
@Entity
public class Friend {
    @Id
    private int id;

    private boolean isDeleted;

    private String photo;

    private Status statusCode;

    private String firstName;

    private String lastName;

    private String city;

    private String country;

    private LocalDateTime birthDate;

    private boolean isOnline;

    private int accountFromId;

    private int accountToId;

    private Status prevStatus;

    private int rating;



}
