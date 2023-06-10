package ru.itgroup.intouch.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.aggregator.config.security.UserDetailsImpl;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.AccountDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountServiceClient accountServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AccountDto accountDto = accountServiceClient.myAccount(username);
        log.info("User with email " +accountDto.getEmail() + " received successfully");
        return new UserDetailsImpl(accountServiceClient.currentUser(username));
    }
}
