package ru.itgroup.intouch.mapper;

import com.googlecode.jmapper.JMapper;
import lombok.RequiredArgsConstructor;
import model.Notification;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;

@Component
@RequiredArgsConstructor
public class NotificationMapper {
    private final UserMapper userMapper;
    private final JMapper<NotificationDto, Notification> mapper = new JMapper<>(
            NotificationDto.class, Notification.class);

    public NotificationDto getDestination(Notification notification) {
        NotificationDto notificationDto = mapper.getDestination(notification);
        notificationDto.setAuthor(userMapper.getDestination(notification.getAuthor()));
        return notificationDto;
    }
}
