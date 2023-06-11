package ru.itgroup.intouch.exceptions;

public class NoUserRegisteredException extends RuntimeException {
    public NoUserRegisteredException(String message) {
        super(message);
    }
}
