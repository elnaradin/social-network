package ru.itgroup.intouch.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AuthenticateDto {
    @Email(message = "Некорректный E-mail")
    private String email;

    private String password;
}
