package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itgroup.intouch.config.FeignConfig;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;
import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.PasswordDto;
import ru.itgroup.intouch.dto.RegistrationDto;

@FeignClient(name = "auth-service", url = "${IP}" + ":" + "${server.port}", path = "/api/v1/auth", configuration = FeignConfig.class)
public interface AuthServiceClient {
    @PostMapping("/login")
    AuthenticateResponseDto login(@RequestBody AuthenticateDto authenticateDto);

    @PostMapping("/register")
    void register(@RequestBody RegistrationDto registrationDto);

    @PostMapping("/password/recovery/")
    void recoverPassword(@RequestBody EmailDto emailDto);

    @PostMapping("/password/recovery/{linkId}")
    void setNewPassword(@PathVariable String linkId,
                        @RequestBody PasswordDto passwordDto);

    @PostMapping("/logout")
    void logout();

    @GetMapping("/captcha")
    CaptchaDto captcha();
}
