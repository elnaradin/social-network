package ru.itgroup.intouch.client.exceptionHandling.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
