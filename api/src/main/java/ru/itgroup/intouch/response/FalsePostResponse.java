package ru.itgroup.intouch.response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FalsePostResponse implements PostResponse{

    private boolean result;
    private String message;

}
