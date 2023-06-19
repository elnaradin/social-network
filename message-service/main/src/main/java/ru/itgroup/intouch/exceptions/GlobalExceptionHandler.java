package ru.itgroup.intouch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.itgroup.intouch.dto.errors.ErrorResponseDTO;
import ru.itgroup.intouch.dto.errors.IllegalRequestParameterException;
import ru.itgroup.intouch.dto.parents.ResponseDTO;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({
            IllegalRequestParameterException.class
    })
    public final ResponseEntity<ResponseDTO> handleException(Exception ex, WebRequest request){
        log.warn("IllegalRequestParameterException in message-service: " +  ex.getMessage());


        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setError(httpStatus.toString());
        response.setErrorDescription(ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

}

