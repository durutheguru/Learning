package com.julianduru.learning.webflux.webfluxzero.exceptionhandler;

import com.julianduru.learning.webflux.webfluxzero.dto.InputFailedValidationResponse;
import com.julianduru.learning.webflux.webfluxzero.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * created by julian on 15/03/2022
 */
@ControllerAdvice
public class InputValidationHandler {


    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex) {
        var response = new InputFailedValidationResponse();
        response.setErrorCode(ex.getErrorCode());
        response.setInput(ex.getInput());
        response.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }


}


