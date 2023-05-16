package ru.itgroup.intouch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.NoUserLoggedInException;
import ru.itgroup.intouch.exceptions.UserAlreadyExistsException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            NoUserLoggedInException.class,
            CaptchaNotValidException.class,
            AuthenticationException.class
    })
    @ResponseBody
    public void handleErrors(Exception e) {
        log.error(e.getMessage());
    }
}
