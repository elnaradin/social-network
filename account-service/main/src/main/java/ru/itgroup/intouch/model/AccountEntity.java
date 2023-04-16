package ru.itgroup.intouch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.itgroup.intouch.dto.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity extends UserEntity{

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
