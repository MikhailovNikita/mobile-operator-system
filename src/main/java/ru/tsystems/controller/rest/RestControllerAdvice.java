package ru.tsystems.controller.rest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger myLogger = Logger.getLogger(RestControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> exceptionClass(final Exception e){
        myLogger.debug(e);
        return new ResponseEntity<>("An unknown error occurred." +
                " Please, write me at nnikita.mikhailov@yandex.ru", HttpStatus.BAD_REQUEST);
    }
}
