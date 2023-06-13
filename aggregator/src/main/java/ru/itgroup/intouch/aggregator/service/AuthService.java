package ru.itgroup.intouch.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.aggregator.config.security.UserDetailsImpl;
import ru.itgroup.intouch.aggregator.config.security.jwt.JWTUtil;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;

    public AuthenticateResponseDto login(AuthenticateDto authenticateDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                userDetailsService.loadUserByUsername(authenticateDto.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateDto.getEmail(),
                        authenticateDto.getPassword())
        );
        AuthenticateResponseDto responseDto = new AuthenticateResponseDto();
        String jwtAccessToken = jwtUtil.generateAccessToken(userDetails.userDto());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(userDetails.userDto());

        responseDto.setAccessToken(jwtAccessToken);
        responseDto.setRefreshToken(jwtRefreshToken);
        return responseDto;
    }
}
