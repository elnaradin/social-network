package ru.itgroup.intouch.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AuthenticateDto {
    @Email(message = "{user.invalid-email}")
    private String email;

    private String password;
}
