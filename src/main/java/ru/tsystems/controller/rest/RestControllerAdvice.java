package ru.tsystems.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tsystems.exceptions.BusinessException;


@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger myLogger = Logger.getLogger(RestControllerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<String> businessExceptionClass(final BusinessException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    protected ResponseEntity<String> unsupportedOperationExceptionClass(final UnsupportedOperationException e){
        return new ResponseEntity<>("Looks like that function is not implemented yet", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> exceptionClass(final Exception e){
        myLogger.debug(e);
        return new ResponseEntity<>("An unknown error occurred." +
                " Please, write me at nnikita.mikhailov@yandex.ru", HttpStatus.BAD_REQUEST);
    }
}
