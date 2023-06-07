package ru.itgroup.intouch.service.account;

import lombok.RequiredArgsConstructor;
import model.account.Account;
import model.account.User;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.UserDto;
import ru.itgroup.intouch.exceptions.NoUserRegisteredException;
import ru.itgroup.intouch.mapper.UserMapper;
import ru.itgroup.intouch.repository.AccountRepository;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountRepository accountRepository;

    public AccountDto getAccountInfo(String email) {
        Optional<Account> account = accountRepository.findFirstByEmail(email);
        if (account.isEmpty()) {
            throw new NoUserRegisteredException("Unable to find data. No user with email \"" +
                    email + "\" found.");
        }
        return userMapper.accountEntityToAccountDto(account.get());
    }

    public UserDto getUserInfo(String email) {
        Optional<User> user = userRepository.findFirstByEmail(email);
        if (user.isEmpty()) {
            throw new NoUserRegisteredException("Unable to find data. No user with email \"" +
                    email + "\" found.");
        }
        return userMapper.userEntity2UserDto(user.get());
    }

    public void updateAccountData(AccountDto accountDto) {
        Optional<Account> account = accountRepository.findFirstByEmail(accountDto.getEmail());
        if (account.isEmpty()) {
            throw new NoUserRegisteredException("Unable to change account info. Email \"" +
                    accountDto.getEmail() + "\" doesn't exist.");
        }
        userMapper.updateAccountFromDto(accountDto, account.get());
        accountRepository.save(account.get());
    }

    public void setAccountDeleted(String email) {
        Optional<Account> firstByEmail = accountRepository.findFirstByEmail(email);
        if (firstByEmail.isEmpty()) {
            throw new NoUserRegisteredException("Unable to soft delete account. No user with mail \"" +
                    email + "\" found.");
        }
        Account account = firstByEmail.get();
        account.setDeleted(true);
        userRepository.save(firstByEmail.get());
    }

    public List<AccountDto> getListOfUsers(List<Long> userIds) {
        List<Account> accounts = accountRepository.findByIdIn(userIds);
        return userMapper.accountsToDtos(accounts);
    }


}
