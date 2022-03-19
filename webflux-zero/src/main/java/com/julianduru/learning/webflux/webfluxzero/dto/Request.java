package com.julianduru.learning.webflux.webfluxzero.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by julian on 18/03/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {


    private Integer first;


    private Integer second;


}

