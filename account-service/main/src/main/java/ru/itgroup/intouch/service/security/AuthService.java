package ru.itgroup.intouch.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.config.SocialNetworkUserDetails;
import ru.itgroup.intouch.config.jwt.JWTUtil;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final SocialNetworkUserDetailsService socialNetworkUserDetailsService;
    private final JWTUtil jwtUtil;

    public AuthenticateResponseDto login(AuthenticateDto authenticateDto) throws UsernameNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateDto.getEmail(),
                authenticateDto.getPassword()));

        SocialNetworkUserDetails userDetails = (SocialNetworkUserDetails)
                socialNetworkUserDetailsService.loadUserByUsername(authenticateDto.getEmail());

        AuthenticateResponseDto responseDto = new AuthenticateResponseDto();


        String jwtAccessToken = jwtUtil.generateAccessToken(userDetails.getUserDto());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUserDto());

        responseDto.setAccessToken(jwtAccessToken);
        responseDto.setRefreshToken(jwtRefreshToken);
        return responseDto;
    }
}
