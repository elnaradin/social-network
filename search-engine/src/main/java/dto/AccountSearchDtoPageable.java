package dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class AccountSearchDtoPageable {
    private AccountSearchDto dto;
    private Pageable pageable;

    public AccountSearchDtoPageable(@RequestBody AccountSearchDto dto, @RequestBody Pageable pageable) {
        this.dto = dto;
        this.pageable = pageable;
    }

}
