package com.julianduru.learning.reactive.ms.orderreactiveservice.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * created by julian on 19/03/2022
 */
@Data
@ToString
@NoArgsConstructor
public class ProductDto {


    private String id;


    private String description;


    private Integer price;


    public ProductDto(String description, Integer price) {
        this.description = description;
        this.price = price;
    }


}

