package ru.itgroup.intouch.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.WsMessageType;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsDto {
    private String type = WsMessageType.NOTIFICATION.name();
    private NotificationDto data;

    public WsDto(NotificationDto notificationDto) {
        data = notificationDto;
    }
}
