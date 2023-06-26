package ru.itgroup.intouch.service.account;

import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.RegistrationDto;

public interface RegistrationService {
    void registerNewUser(RegistrationDto registrationDto);
    CaptchaDto generateCaptcha();
}
