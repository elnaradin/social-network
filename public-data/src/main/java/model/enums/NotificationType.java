package model.enums;

import lombok.Getter;

public enum NotificationType {
    POST("post"),
    POST_COMMENT("postComment"),
    COMMENT_COMMENT("commentComment"),
    MESSAGE("message"),
    FRIEND_REQUEST("friendRequest"),
    FRIEND_BIRTHDAY("friendBirthday"),
    SEND_EMAIL_MESSAGE("sendEmailMessage");

    @Getter
    private final String label;

    NotificationType(String label) {
        this.label = label;
    }
}
