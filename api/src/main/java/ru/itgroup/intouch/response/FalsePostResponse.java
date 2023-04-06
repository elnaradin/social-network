package ru.itgroup.intouch.response;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class FalsePostResponse extends AbstractResponse implements PostResponse {
    public FalsePostResponse(boolean result, String message) {
        super(result, message);
    }
}
