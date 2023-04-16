package ru.itgroup.intouch.dto;

import lombok.Data;

@Data
public class AuthenticateDto {
    private String email;
    private String password;
}
