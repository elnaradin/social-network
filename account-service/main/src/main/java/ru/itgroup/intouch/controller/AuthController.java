package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.PasswordDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.UserAlreadyRegisteredException;
import ru.itgroup.intouch.service.account.PasswordRecoveryService;
import ru.itgroup.intouch.service.account.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final RegistrationService registrationService;
    private final PasswordRecoveryService passwordRecoveryService;


    @PostMapping("/register")
    public void register(@RequestBody RegistrationDto registrationDto)
            throws UserAlreadyRegisteredException, CaptchaNotValidException {
        registrationService.registerNewUser(registrationDto);
    }

    @PostMapping("/password/recovery/")
    public void recoverPassword(@RequestBody EmailDto emailDto) {
        passwordRecoveryService.sendLetter(emailDto.getEmail());

    }

    @PostMapping("/password/recovery/{linkId}")
    public void setNewPassword(@PathVariable String linkId,
                               @RequestBody PasswordDto passwordDto) {
        passwordRecoveryService.setNewPassword(linkId, passwordDto.getPassword());
    }


    // TODO: 22.04.2023 finish captcha
    @GetMapping("/captcha")
    public CaptchaDto captcha() {
        CaptchaDto captchaDto = new CaptchaDto("smwm",
                "https://www.researchgate.net/publication/220459388/figure/fig1/AS:" +
                        "305672878084096@1449889501199/Example-of-a-graphical-CAPTCHA-a-Com-" +
                        "pletely-Automated-Public-Turing-Test-to-Tell.png");
        log.info("captcha returned");
        return captchaDto;
    }

}

