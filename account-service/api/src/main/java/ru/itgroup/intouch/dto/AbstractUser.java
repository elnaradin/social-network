package ru.itgroup.intouch.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public abstract class AbstractUser {
    private Integer id;
    @NotBlank(message = "Поле \"Имя\" не заполнено")
    private String firstName;
    @NotBlank(message = "Поле \"Фамилия\" не заполнено")
    private String lastName;
    @Email(message = "Некорректный E-mail")
    private String email;

}
