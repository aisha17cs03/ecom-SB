package com.ecommerce.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// this class is used to handle the global exception handling it will handle all the exception that are thrown in the application
// @RestControllerAdvice is specialized  version of @ControllerAdvice
@RestControllerAdvice

public class MyGlobalExceptionHandler {
    //@ExceptionHandler is used to handle the specific exception
    // This method will handle the MethodArgumentNotValidException which is thrown when the request body is not valid.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //ResponseEntity is used to return the response to the client and handle the status code as well
    public ResponseEntity <Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            //getDefaultMessage is used to get the default message of the error if we want to custom message we can add it
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }
}
