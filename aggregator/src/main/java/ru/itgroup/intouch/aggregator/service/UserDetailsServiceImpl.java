package ru.itgroup.intouch.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.aggregator.config.security.UserDetailsImpl;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.dto.UserDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountServiceClient accountServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto userDto = accountServiceClient.currentUser(username);
        log.info("User with email " +userDto.getEmail() + " received successfully");
        return new UserDetailsImpl(userDto);
    }
}
