package com.julianduru.learning.reactive.ms.orderreactiveservice.entity;

import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * created by julian on 23/03/2022
 */
@Entity
@Data
@ToString
public class PurchaseOrder {


    @Id
    @GeneratedValue
    private Integer id;


    private String productId;


    private Integer userId;


    private Integer amount;


    private OrderStatus status;



}
