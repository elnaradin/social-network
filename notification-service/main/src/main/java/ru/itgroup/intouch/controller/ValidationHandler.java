package ru.itgroup.intouch.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> addError(errors, error));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private static void addError(@NotNull Map<String, String> errors, @NotNull ObjectError error) {
        String fieldName = ((FieldError) error).getField();
        String message = error.getDefaultMessage();
        errors.put(fieldName, message);
    }
}
