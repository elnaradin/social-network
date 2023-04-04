package ru.itgroup.intouch.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.itgroup.intouch.dto.PostDto;

@AllArgsConstructor
@Getter
public class TruePostResponse implements PostResponse{

    private boolean result;
    private PostDto postDto;
    private String message;



}
