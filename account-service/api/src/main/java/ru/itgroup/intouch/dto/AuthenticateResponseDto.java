package ru.itgroup.intouch.dto;

import lombok.Data;

@Data
public class AuthenticateResponseDto {
    private String accessToken;
    private String refreshToken;
}
