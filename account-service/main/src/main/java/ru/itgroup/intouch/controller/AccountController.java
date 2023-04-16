package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.AccountDto;
import ru.itgroup.intouch.service.registration.RegistrationService;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final RegistrationService registrationService;
    @GetMapping("/api/v1/account/me")
    public AccountDto myAccount() {
        return registrationService.getUserInfo();
    }
}
