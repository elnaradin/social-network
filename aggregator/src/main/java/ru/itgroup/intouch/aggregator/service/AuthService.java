package ru.itgroup.intouch.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.aggregator.config.security.UserDetailsImpl;
import ru.itgroup.intouch.aggregator.config.security.jwt.JWTUtil;
import ru.itgroup.intouch.client.AccountServiceClient;
import ru.itgroup.intouch.client.exceptionHandling.exceptions.BadRequestException;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;
    private final AccountServiceClient accountServiceClient;

    public AuthenticateResponseDto login(AuthenticateDto authenticateDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                userDetailsService.loadUserByUsername(authenticateDto.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticateDto.getEmail(),
                            authenticateDto.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadRequestException("Неверный логин или пароль");
        }

        AuthenticateResponseDto responseDto = new AuthenticateResponseDto();
        String jwtAccessToken = jwtUtil.generateAccessToken(userDetails.userDto());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(userDetails.userDto());

        responseDto.setAccessToken(jwtAccessToken);
        responseDto.setRefreshToken(jwtRefreshToken);

        changeIsOnline(authenticateDto.getEmail(), true);
        return responseDto;
    }

    public void logout(String email) {
        changeIsOnline(email, false);
    }

    private void changeIsOnline(String email, boolean isOnline) {

        AccountDto accountDto = accountServiceClient.myAccount(email);
        accountDto.setOnline(isOnline);
        accountDto.setLastOnlineTime(LocalDateTime.now(ZoneId.of("Europe/Moscow")).toString());
        accountServiceClient.changeProfile(accountDto);
    }
}
