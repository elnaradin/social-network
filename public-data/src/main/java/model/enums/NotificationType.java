package model.enums;

import lombok.Getter;

public enum NotificationType {
    POST("post"),
    POST_COMMENT("postComment"),
    COMMENT_COMMENT("commentComment"),
    MESSAGE("message"),
    FRIEND_REQUEST("friendRequest"),
    FRIEND_BIRTHDAY("friendBirthday");

    @Getter
    private final String label;

    NotificationType(String label) {
        this.label = label;
    }
}
