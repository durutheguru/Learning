package com.julianduru.learning.reactive.ms.productreactiveservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * created by julian on 19/03/2022
 */
@Data
@ToString
public class Product {


    @Id
    private String id;


    private String description;


    private Integer price;



}
