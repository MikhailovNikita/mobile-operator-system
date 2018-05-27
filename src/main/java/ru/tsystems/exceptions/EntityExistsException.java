package ru.tsystems.exceptions;

public class EntityExistsException extends BusinessLogicException{
    public EntityExistsException(String message){
        super(message);
    }

}
