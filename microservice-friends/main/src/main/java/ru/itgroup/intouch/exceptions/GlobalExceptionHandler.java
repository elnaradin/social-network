package ru.itgroup.intouch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.response.ApiResponse;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserNotFoundException.class,
            AuthenticationException.class,
            WrongParameterException.class,
            FriendServiceException.class
    })
    public final ResponseEntity<ApiResponse<FriendDto>> handleException(Exception ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (ex instanceof UserNotFoundException) {
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            UserNotFoundException userNotFoundException = (UserNotFoundException) ex;
            log.warn("UserNotFoundException " + ex.getMessage());
            return handleExceptionWithNullBody(userNotFoundException, httpHeaders, httpStatus, request);
        }

        if (ex instanceof AuthenticationException) {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            AuthenticationException authenticationException = (AuthenticationException) ex;
            log.warn("AuthenticationException " + ex.getMessage());
            return handleExceptionWithNullBody(authenticationException, httpHeaders, httpStatus, request);
        }

        if (ex instanceof WrongParameterException) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            WrongParameterException wrongParameterException = (WrongParameterException) ex;
            log.warn("WrongParameterException " + ex.getMessage());
            return handleExceptionWithNullBody(wrongParameterException, httpHeaders, httpStatus, request);
        }

        if (ex instanceof FriendServiceException) {
            HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
            FriendServiceException friendServiceException = (FriendServiceException) ex;
            log.warn("FriendServiceException " + ex.getMessage());
            return handleExceptionWithNullBody(friendServiceException, httpHeaders, httpStatus, request);
        }

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, null, httpHeaders, httpStatus, request);
    }

    private ResponseEntity<ApiResponse<FriendDto>> handleExceptionWithNullBody
            (Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<FriendDto> response = new ApiResponse<>();
        response.setMessage(status.toString());
        response.setTimestamp(LocalDateTime.now());
        response.setData(null);
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    protected ResponseEntity<ApiResponse<FriendDto>> handleExceptionInternal
            (Exception ex, ApiResponse<FriendDto> body, HttpHeaders headers,
             HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex.getMessage(), RequestAttributes.SCOPE_REQUEST);
        }
        ex.printStackTrace();
        return new ResponseEntity<>(body, headers, status);
    }
}
