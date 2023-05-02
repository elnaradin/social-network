package ru.itgroup.intouch.exceptions;

public class CaptchaNotValidException extends Exception {
    public CaptchaNotValidException(String message) {
        super(message);
    }
}
