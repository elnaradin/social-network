package ru.itgroup.intouch.exceptions;

public class NoEmailFoundException extends RuntimeException {
    public NoEmailFoundException(String message) {
        super(message);
    }
}
