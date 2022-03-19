package com.julianduru.learning.webflux.webfluxzero.dto;

import lombok.Data;
import lombok.ToString;

/**
 * created by julian on 15/03/2022
 */
@Data
@ToString
public class InputFailedValidationResponse {


    private int errorCode;


    private int input;


    private String message;


}


