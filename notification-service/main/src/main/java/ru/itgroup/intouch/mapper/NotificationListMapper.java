package ru.itgroup.intouch.mapper;

import lombok.RequiredArgsConstructor;
import model.Notification;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.contracts.mapper.ListResponseMapper;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationListMapper implements ListResponseMapper<Notification> {
    private final NotificationMapper notificationMapper;

    public NotificationListDto getDestination(@NotNull List<Notification> notifications) {
        List<NotificationDto> notificationDtoList = notifications.stream()
                .map(notificationMapper::getDestination)
                .toList();

        return NotificationListDto.builder().data(notificationDtoList).build();
    }
}
