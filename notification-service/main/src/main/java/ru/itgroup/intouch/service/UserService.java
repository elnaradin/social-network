package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.account.Account;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccountRepository accountRepository;

    public Account getUser(Long userId) {
        return accountRepository.findById(userId).orElse(null);
    }
}
