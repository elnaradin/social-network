package ru.itgroup.intouch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.NoUserLoggedInException;
import ru.itgroup.intouch.exceptions.UserAlreadyExistsException;


@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            NoUserLoggedInException.class,
            CaptchaNotValidException.class,
            AuthenticationException.class
    })
    @ResponseBody
    public ResponseEntity<?> handleUserAlreadyExists(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


}
