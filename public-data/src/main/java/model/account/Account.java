package model.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.Status;

import java.time.LocalDateTime;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends User {

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
