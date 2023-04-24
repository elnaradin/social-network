package ru.itgroup.intouch.exceptions;

public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String massage) {
        super(massage);
    }
}