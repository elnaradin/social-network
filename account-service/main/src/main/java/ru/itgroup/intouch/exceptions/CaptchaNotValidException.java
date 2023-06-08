package ru.itgroup.intouch.exceptions;

public class CaptchaNotValidException extends RuntimeException {
    public CaptchaNotValidException(String message) {
        super(message);
    }
}
