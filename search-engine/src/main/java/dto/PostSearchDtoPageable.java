package dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class PostSearchDtoPageable {

    private PostSearchDto dto;
    private Pageable pageable;

    public PostSearchDtoPageable(@RequestBody PostSearchDto dto, @RequestBody Pageable pageable) {
        this.dto = dto;
        this.pageable = pageable;
    }
}
