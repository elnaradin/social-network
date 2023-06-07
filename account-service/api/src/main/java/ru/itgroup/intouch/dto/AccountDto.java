package ru.itgroup.intouch.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AccountDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isDeleted;
    @JsonIgnore
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
