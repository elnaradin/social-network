package ru.itgroup.intouch.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class AccountDto extends AbstractUser {
    private int id;
    private boolean isDeleted;
    private String password;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String statusCode;
    private String regDate;
    private String birthDate;
    private String messagePermission;
    private String lastOnlineTime;
    private boolean isOnline;
    private boolean isBlocked;
    private String photoId;
    private String photoName;
    private String createdOn;
    private String updateOn;
}
