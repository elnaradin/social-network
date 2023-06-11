package ru.itgroup.intouch.mapper;

import com.googlecode.jmapper.JMapper;
import lombok.RequiredArgsConstructor;
import model.Notification;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;

@Component
@RequiredArgsConstructor
public class NotificationMapper {
    private final AccountMapper accountMapper;
    private final JMapper<NotificationDto, Notification> mapper = new JMapper<>(
            NotificationDto.class,
            Notification.class
    );

    public NotificationDto getDestination(Notification notification) {
        NotificationDto notificationDto = mapper.getDestination(notification);
        notificationDto.setReceiverId(notification.getReceiver().getId());
        notificationDto.setAuthor(accountMapper.getDestination(notification.getAuthor()));
        return notificationDto;
    }
}
