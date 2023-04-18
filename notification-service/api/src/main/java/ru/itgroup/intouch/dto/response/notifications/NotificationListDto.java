package ru.itgroup.intouch.dto.response.notifications;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.itgroup.intouch.dto.response.ResponseDto;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class NotificationListDto extends ResponseDto {
    private List<NotificationDto> data;
}

