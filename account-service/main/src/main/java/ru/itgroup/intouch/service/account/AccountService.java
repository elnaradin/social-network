package ru.itgroup.intouch.service.account;

import lombok.RequiredArgsConstructor;
import model.account.Account;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.exceptions.NoUserLoggedInException;
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

    public AccountDto getUserInfo() throws NoUserLoggedInException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            throw new NoUserLoggedInException("No user logged in. Authorization failed");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Account> account = accountRepository.findFirstByEmail(userDetails.getUsername());
        if (account.isEmpty()) {
            throw new UsernameNotFoundException("Account not found. No such email in database");
        }
        return userMapper.accountEntityToAccountDto(account.get());
    }

    public void updateAccountData(AccountDto accountDto) throws NoUserLoggedInException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            throw new NoUserLoggedInException("No user currently logged in to update");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Account> byId = accountRepository.findFirstByEmail(userDetails.getUsername());
        if (byId.isEmpty()) {
            throw new UsernameNotFoundException("account does not exist");
        }
        userMapper.updateAccountFromDto(accountDto, byId.get());
        accountRepository.save(byId.get());
    }

    public void setAccountDeleted() throws NoUserLoggedInException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof String) {
            throw new NoUserLoggedInException("No user currently logged in to delete");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Account> firstByEmail = accountRepository.findFirstByEmail(userDetails.getUsername());
        if (firstByEmail.isEmpty()) {
            throw new UsernameNotFoundException("no user found by email");
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
