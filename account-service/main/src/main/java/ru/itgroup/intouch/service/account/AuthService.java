package ru.itgroup.intouch.service.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.config.UserDetailsImpl;
import ru.itgroup.intouch.config.jwt.JWTUtil;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;
import ru.itgroup.intouch.service.security.UserDetailsServiceImpl;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;
    public AuthenticateResponseDto login(AuthenticateDto authenticateDto) throws AuthenticationException {
        log.info("login started");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateDto.getEmail(),
                authenticateDto.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl)
                userDetailsService.loadUserByUsername(authenticateDto.getEmail());

        AuthenticateResponseDto responseDto = new AuthenticateResponseDto();
        String jwtAccessToken = jwtUtil.generateAccessToken(userDetails.userDto());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(userDetails.userDto());

        log.info("JWTs generated successfully");
        responseDto.setAccessToken(jwtAccessToken);
        responseDto.setRefreshToken(jwtRefreshToken);
        return responseDto;
    }
}
