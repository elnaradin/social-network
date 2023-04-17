package ru.itgroup.intouch.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractResponse {
    private boolean result;
    private String message;

}
