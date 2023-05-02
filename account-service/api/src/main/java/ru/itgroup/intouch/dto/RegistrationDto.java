package ru.itgroup.intouch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegistrationDto extends AbstractUser {
    private String password1;
    private String password2;
    private String captchaCode;
    private String captchaSecret;
}
