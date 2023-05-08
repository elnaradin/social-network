package ru.itgroup.intouch.aggregator.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itgroup.intouch.exceptions.NotFoundException;
import ru.itgroup.intouch.exceptions.UnauthorizedException;

import java.net.ConnectException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ConnectException.class})
    private ResponseEntity<?> handleConnectException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @ExceptionHandler({NotFoundException.class})
    private ResponseEntity<?> handleNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @ExceptionHandler({UnauthorizedException.class})
    private ResponseEntity<?> handleUnauthorizedException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler({FeignException.Unauthorized.class})
    private ResponseEntity<?> handleBadRequestException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
