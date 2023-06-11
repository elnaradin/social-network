package ru.itgroup.intouch.aggregator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.aggregator.service.AuthService;
import ru.itgroup.intouch.client.AuthServiceClient;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;
import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.PasswordDto;
import ru.itgroup.intouch.dto.RegistrationDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("${server.api.prefix}/auth")
@Slf4j
public class AuthorizationController {
    private final AuthServiceClient authServiceClient;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticateResponseDto login(@RequestBody AuthenticateDto authenticateDto) {
        return authService.login(authenticateDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody RegistrationDto registrationDto) {
        registrationDto.setPassword1(passwordEncoder.encode(registrationDto.getPassword1()));
        authServiceClient.register(registrationDto);
    }

    @PostMapping("/password/recovery/")
    @ResponseStatus(HttpStatus.OK)
    public void recoverPassword(@RequestBody EmailDto emailDto) {
        authServiceClient.recoverPassword(emailDto);
    }

    @PostMapping("/password/recovery/{linkId}")
    @ResponseStatus(HttpStatus.OK)
    public void setNewPassword(@PathVariable String linkId,
                               @RequestBody PasswordDto passwordDto) {
        passwordDto.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        authServiceClient.setNewPassword(linkId, passwordDto);
    }

    @GetMapping("/captcha")
    @ResponseStatus(HttpStatus.OK)
    public CaptchaDto captcha() {
        return authServiceClient.captcha();
    }

}
