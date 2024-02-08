package ru.itgroup.intouch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto extends AbstractUser {
    @Pattern(regexp = ".{8,}",
            message = "{user.invalid-password}")
    private String password1;
    private String password2;
    @NotBlank(message = "{captcha.empty}")
    private String captchaCode;
    private String captchaSecret;
}
