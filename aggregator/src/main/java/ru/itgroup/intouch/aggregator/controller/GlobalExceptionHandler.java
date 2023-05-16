package ru.itgroup.intouch.aggregator.controller;

import feign.FeignException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.NoAccountFoundException;
import ru.itgroup.intouch.exceptions.NoUserLoggedInException;
import ru.itgroup.intouch.exceptions.UserAlreadyExistsException;

import java.net.ConnectException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ConnectException.class})
    private ResponseEntity<?> handleConnectException(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @ExceptionHandler({FeignException.BadRequest.class})
    private ResponseEntity<?> handleBadRequestException(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @ExceptionHandler({FeignException.Forbidden.class})
    private ResponseEntity<?> handleForbiddenException(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @ExceptionHandler({FeignException.Unauthorized.class,
            CaptchaNotValidException.class, NoUserLoggedInException.class,
            NoAccountFoundException.class, UserAlreadyExistsException.class})
    private ResponseEntity<?> handleUnauthorizedException(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler({FeignException.InternalServerError.class})
    private ResponseEntity<?> handleInternalServerError(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @ExceptionHandler({FeignException.NotFound.class})
    private ResponseEntity<?> handleNotFoundError(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
