package ru.itgroup.intouch.mapper;

import com.googlecode.jmapper.JMapper;
import model.account.Account;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.response.notifications.AuthorDto;

@Component
public class AccountMapper {
    private final JMapper<AuthorDto, Account> mapper = new JMapper<>(AuthorDto.class, Account.class);

    public AuthorDto getDestination(Account account) {
        return mapper.getDestination(account);
    }
}
