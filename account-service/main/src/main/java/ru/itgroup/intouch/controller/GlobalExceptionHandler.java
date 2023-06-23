package ru.itgroup.intouch.controller;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itgroup.intouch.client.exceptionHandling.ErrorResponse;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.NoEmailFoundException;
import ru.itgroup.intouch.exceptions.NoUserRegisteredException;
import ru.itgroup.intouch.exceptions.ServiceUnavailableException;
import ru.itgroup.intouch.exceptions.UserAlreadyRegisteredException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private HttpStatus status;

    @ExceptionHandler({CaptchaNotValidException.class,
            UserAlreadyRegisteredException.class,
            NoEmailFoundException.class,
            UserAlreadyRegisteredException.class,
            MessagingException.class,
            NoUserRegisteredException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception,
                                                          WebRequest request) {
        status = HttpStatus.BAD_REQUEST;
        log.warn(exception.getMessage());
        return ResponseEntity.status(status).body(new ErrorResponse(
                status.value(),
                status.name(),
                "<br/>" + exception.getMessage(),
                request.getDescription(false)));
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleConnectException(ServiceUnavailableException e,
                                                                WebRequest request){
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error(e.getLocalizedMessage());
        return ResponseEntity.status(status).body(new ErrorResponse(
                status.value(),
                status.name(),
                e.getLocalizedMessage(),
                request.getDescription(false)));
    }
}
