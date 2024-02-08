package ru.itgroup.intouch.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@ToString
public class AccountDto extends AbstractUser{
    private boolean isDeleted;
    @JsonIgnore
    private String password;
    @Pattern(regexp = "(7?[0-9]{10}|^$)",
            message = "{user.invalid-phone}")
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String statusCode;
    private String regDate;
    @Pattern(regexp = "\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+Z",
    message = "{user.invalid-birthdate}")
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
