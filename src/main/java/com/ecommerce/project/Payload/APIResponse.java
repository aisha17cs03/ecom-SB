package com.ecommerce.project.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    //Creating a custom response class to send the response
    //myGlobalExceptionHandler will make use of this class to send the response
    private String message;
    private boolean status;
}
