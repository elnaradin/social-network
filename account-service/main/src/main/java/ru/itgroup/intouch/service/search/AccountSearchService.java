package ru.itgroup.intouch.service.search;

import dto.AccountSearchDtoPageable;
import org.springframework.data.domain.Page;
import ru.itgroup.intouch.dto.AccountDto;

public interface AccountSearchService {
    Page<AccountDto> getAccountResponse(AccountSearchDtoPageable dto);
}
