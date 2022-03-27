package com.julianduru.learning.reactive.ms.orderreactiveservice.dto;

import lombok.Data;
import lombok.ToString;

/**
 * created by julian on 23/03/2022
 */
@Data
@ToString
public class PurchaseOrderRequestDto {


    private Integer userId;


    private String productId;


}
