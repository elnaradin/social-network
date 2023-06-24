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
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private HttpStatus status;
    @ExceptionHandler({AccountServiceUnavailableException.class, ConnectException.class})
    private ResponseEntity<ErrorResponse> handleConnectException(Exception e,
                                                                 WebRequest request) {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.warn(e.getLocalizedMessage());
        return ResponseEntity
                .status(status)
                .body(
                        new ErrorResponse(status.value(),
                                status.name(),
                                e.getLocalizedMessage(),
                                request.getDescription(false)
                        )
                );
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBadRequest(RuntimeException e,
                                              WebRequest request) {
        status = HttpStatus.BAD_REQUEST;
        log.warn(e.getMessage());
        return ResponseEntity
                .status(status)
                .body(
                        new ErrorResponse(status.value(),
                                status.name(),
                                e.getMessage(),
                                request.getDescription(false)
                        )
                );
    }
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e,
                                              WebRequest request) {
        status = HttpStatus.UNAUTHORIZED;
        log.warn(e.getMessage());
        return ResponseEntity
                .status(status)
                .body(
                        new ErrorResponse(status.value(),
                                status.name(),
                                e.getMessage(),
                                request.getDescription(false)
                        )
                );
    }



}
