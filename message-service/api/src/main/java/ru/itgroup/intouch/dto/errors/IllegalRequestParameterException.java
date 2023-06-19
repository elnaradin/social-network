package ru.itgroup.intouch.dto.errors;

public class IllegalRequestParameterException extends IllegalArgumentException{
    public IllegalRequestParameterException(String message) {
        super(message);
    }
}

