package dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class AccountSearchDtoPageable {
    AccountSearchDto dto;
    Pageable pageable;

    public AccountSearchDtoPageable(@RequestBody AccountSearchDto dto, @RequestBody Pageable pageable) {
        this.dto = dto;
        this.pageable = pageable;
    }

}
