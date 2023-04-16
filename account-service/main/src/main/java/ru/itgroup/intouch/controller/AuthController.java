package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.service.registration.RegistrationService;
import ru.itgroup.intouch.service.security.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticateDto authenticateDto) {
        try {
            AuthenticateResponseDto authenticateResponseDto = authService.login(authenticateDto);
            return ResponseEntity.ok().body(authenticateResponseDto);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto registrationDto) {
        registrationService.registerNewUser(registrationDto);
        return ResponseEntity.ok().build();

    }

}
