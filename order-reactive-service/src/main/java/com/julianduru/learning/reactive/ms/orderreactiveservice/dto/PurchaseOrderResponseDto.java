package com.julianduru.learning.reactive.ms.orderreactiveservice.dto;

import lombok.Data;
import lombok.ToString;

/**
 * created by julian on 23/03/2022
 */
@Data
@ToString
public class PurchaseOrderResponseDto {


    private Integer orderId;


    private Integer userId;


    private String productId;


    private Integer amount;


    private OrderStatus status;


}
