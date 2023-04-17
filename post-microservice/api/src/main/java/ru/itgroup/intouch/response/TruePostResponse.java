package ru.itgroup.intouch.response;
import lombok.Getter;
import ru.itgroup.intouch.dto.PostDto;

@Getter
public class TruePostResponse extends AbstractResponse implements PostResponse{
    private final PostDto postDto;
    public TruePostResponse(boolean b, String message, PostDto postDto) {
        super(b, message);
        this.postDto = postDto;
    }

}
