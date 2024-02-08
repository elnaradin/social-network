package ru.itgroup.intouch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUser {
    private Integer id;
    @NotBlank(message = "{user.empty-first-name}")
    private String firstName;
    @NotBlank(message = "{user.empty-last-name}")
    private String lastName;
    @Email(message = "{user.invalid-email}")
    private String email;

}
