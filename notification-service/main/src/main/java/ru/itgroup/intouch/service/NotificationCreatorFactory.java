package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.enums.NotificationType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.annotation.Loggable;
import ru.itgroup.intouch.contracts.service.creators.NotificationCreator;
import ru.itgroup.intouch.service.creators.CommentCommentNotificationCreator;
import ru.itgroup.intouch.service.creators.FriendRequestNotificationCreator;
import ru.itgroup.intouch.service.creators.MessageNotificationCreator;
import ru.itgroup.intouch.service.creators.PostCommentNotificationCreator;
import ru.itgroup.intouch.service.creators.PostNotificationCreator;

@Service
@RequiredArgsConstructor
public class NotificationCreatorFactory {
    private final ApplicationContext context;

    @Loggable
    public NotificationCreator getNotificationCreator(String type) throws ClassNotFoundException {
        return switch (NotificationType.valueOf(type)) {
            case POST -> context.getBean(PostNotificationCreator.class);
            case POST_COMMENT -> context.getBean(PostCommentNotificationCreator.class);
            case COMMENT_COMMENT -> context.getBean(CommentCommentNotificationCreator.class);
            case MESSAGE -> context.getBean(MessageNotificationCreator.class);
            case FRIEND_REQUEST -> context.getBean(FriendRequestNotificationCreator.class);
            default -> throw new ClassNotFoundException("Notification creator not found");
        };
    }
}
