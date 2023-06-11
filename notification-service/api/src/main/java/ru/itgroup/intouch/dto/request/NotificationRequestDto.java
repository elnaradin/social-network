package ru.itgroup.intouch.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import model.enums.NotificationType;
import org.springframework.validation.annotation.Validated;
import ru.itgroup.intouch.annotations.ValidEnum;

@Data
@Validated
public class NotificationRequestDto {
    @NotNull(message = "Author id can't be null")
    @Min(value = 1, message = "Author id can't be less then 0")
    private Long authorId;

    @Min(value = 1, message = "Receiver id can't be less then 0")
    private Long receiverId;

    @NotNull(message = "Value can't be null")
    @NotBlank(message = "Value can't be empty")
    @ValidEnum(enumClass = NotificationType.class)
    private String notificationType;

    @Min(value = 1, message = "Receiver id can't be less then 0")
    private Long entityId;
}
