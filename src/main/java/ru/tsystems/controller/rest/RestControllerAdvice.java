package ru.tsystems.controller.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tsystems.exceptions.BusinessLogicException;


@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BusinessLogicException.class)
    protected ResponseEntity<String> businessExceptionClass(final BusinessLogicException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    protected ResponseEntity<String> unsupportedOperationExceptionClass(final UnsupportedOperationException e){
        return new ResponseEntity<>("Looks like that function is not implemented yet", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> exceptionClass(final Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
