package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import model.enums.Status;

import java.time.LocalDateTime;

@Data
@Entity
public class Account {

   @Id
   private int id;

   private boolean isDeleted;

   private String firstname;

   private String lastName;

   private String email;

   private String password;

   private String phone;

   private String photo;

   private String about;

   private String city;

   private String country;

   private Status statusCode;

   private LocalDateTime regDate;

   private LocalDateTime birthDate;

   private String messagePermission;

   private LocalDateTime lastOnlineTime;

   private boolean isOnline;

   private boolean isBlocked;

   private String photoId;

   private String photoName;

   private LocalDateTime createdOn;

   private LocalDateTime updateOn;

}
