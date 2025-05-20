package com.ecommerce.project.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    // this class is used to handle the resource not found exception
    // this class will be used to throw the exception when the resource is not found
    // this class will extend the RuntimeException class
    // this class will be used to handle the exception globally
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;


    //No-args constructor
    public ResourceNotFoundException() {

    }

    //Parameterized constructor

    public ResourceNotFoundException(String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s : %s", resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }


    //Parameterized constructor
    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldId) {
        super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }
}
