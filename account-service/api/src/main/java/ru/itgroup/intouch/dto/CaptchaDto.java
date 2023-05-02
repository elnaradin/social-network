package ru.itgroup.intouch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaDto {
    private String secret;
    private String image;
}
