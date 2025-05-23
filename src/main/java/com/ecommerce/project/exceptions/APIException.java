package com.ecommerce.project.exceptions;

public class APIException extends RuntimeException{
    // this class is used to handle the API exception
    private static final long SerialVersionUID = 1L;

    // this class will be used to throw the exception when the API is not found
    public APIException() {
    }

    //Parameterized constructor
    public APIException(String message) {
        super(message);
    }
}
