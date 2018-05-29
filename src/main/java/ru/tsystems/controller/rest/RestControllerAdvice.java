package ru.tsystems.controller.rest;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tsystems.exceptions.BusinessLogicException;

import javax.persistence.PersistenceException;


@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger restLogger = Logger.getLogger(RestControllerAdvice.class);

    @ExceptionHandler(BusinessLogicException.class)
    protected ResponseEntity<String> businessExceptionClass(final BusinessLogicException e){
        restLogger.debug(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    protected ResponseEntity<String> unsupportedOperationExceptionClass(final UnsupportedOperationException e){
        restLogger.debug(e.getMessage());
        return new ResponseEntity<>("Looks like that function is not implemented yet", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersistenceException.class)
    protected ResponseEntity<String> persistenceExceptionClass(final PersistenceException e){
        restLogger.warn(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> exceptionClass(final Exception e){
        restLogger.error(e.getMessage());
        return new ResponseEntity<>("Whoops! Something went really wrong, please contact me at nnikita.mikhailov@yandex.ru", HttpStatus.BAD_REQUEST);
    }
}
