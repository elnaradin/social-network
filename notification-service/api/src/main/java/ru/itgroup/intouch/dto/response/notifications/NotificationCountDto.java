package ru.itgroup.intouch.dto.response.notifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.ResponseDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationCountDto extends ResponseDto {
    private CountDto data;
}
