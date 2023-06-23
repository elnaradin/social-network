package ru.itgroup.intouch.aggregator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<AuthenticateResponseDto> login(@Valid @RequestBody AuthenticateDto authenticateDto) {
        return ResponseEntity.ok().body(authService.login(authenticateDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        authService.logout(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDto registrationDto) {
        registrationDto.setPassword1(passwordEncoder.encode(registrationDto.getPassword1()));
        authServiceClient.register(registrationDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/recovery/")
    public ResponseEntity<?> recoverPassword(@Valid @RequestBody EmailDto emailDto) {
        authServiceClient.recoverPassword(emailDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/recovery/{linkId}")
    public ResponseEntity<?> setNewPassword(@PathVariable String linkId,
                                            @Valid @RequestBody PasswordDto passwordDto) {
        passwordDto.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
        authServiceClient.setNewPassword(linkId, passwordDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> captcha() {
        return ResponseEntity.ok().body(authServiceClient.captcha());
    }

    @PostMapping("/change-password-link")
    public ResponseEntity<?> changePassword(@Valid @RequestBody EmailDto emailDto) {
        authServiceClient.changePassword(emailDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-email-link")
    public ResponseEntity<?> changeEmail(@Valid @RequestBody EmailDto emailDto) {
        authServiceClient.changeEmail(emailDto);
        return ResponseEntity.ok().build();
    }

}
