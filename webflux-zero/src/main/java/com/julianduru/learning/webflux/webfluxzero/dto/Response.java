package com.julianduru.learning.webflux.webfluxzero.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * created by julian on 08/03/2022
 */
@Data
@ToString
@NoArgsConstructor
public class Response {


    private Date date = new Date();
    private int output;


    public Response(int output) {
        this.output = output;
    }


}
