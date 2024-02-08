package ru.itgroup.intouch.service.account;

import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.UserDto;

import java.util.List;

public interface AccountService {
    AccountDto getAccountInfo(String email);

    UserDto getUserInfo(String email);

    void updateAccountData(AccountDto accountDto);

    void setAccountDeleted(String email);

    List<AccountDto> getListOfUsers(List<Long> userIds);

    AccountDto getAccountInfoById(Long id);
}
