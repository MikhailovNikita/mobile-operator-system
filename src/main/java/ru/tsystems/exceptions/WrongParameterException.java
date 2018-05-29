package ru.tsystems.exceptions;

public class WrongParameterException extends BusinessLogicException {

    public WrongParameterException(String message){
        super(message);
    }
}
