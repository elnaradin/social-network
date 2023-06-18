package ru.itgroup.intouch.aggregator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.itgroup.intouch.client.exceptionHandling.ErrorResponse;
import ru.itgroup.intouch.client.exceptionHandling.exceptions.AccountServiceUnavailableException;
import ru.itgroup.intouch.client.exceptionHandling.exceptions.BadRequestException;

import java.net.ConnectException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({AccountServiceUnavailableException.class, ConnectException.class})
    private ResponseEntity<ErrorResponse> handleConnectException(Exception e,
                                                                 WebRequest request) {
        log.warn(e.getLocalizedMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                e.getLocalizedMessage(),
                                request.getDescription(false)
                        )
                );
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBadRequest(RuntimeException e,
                                              WebRequest request) {
        log.warn(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.name(),
                                e.getMessage(),
                                request.getDescription(false)
                        )
                );
    }



}
