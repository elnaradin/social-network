package model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    FRIEND("FRIEND"),
    REQUEST_TO("REQUEST_TO"),
    REQUEST_FROM("REQUEST_FROM"),
    BLOCKED("BLOCKED"),
    DECLINED("DECLINED"),
    SUBSCRIBED("SUBSCRIBED"),
    NONE("NONE"),
    WATCHING("WATCHING"),
    REJECTING("REJECTING"),
    RECOMMENDATION("RECOMMENDATION");

    private final String status;
}
