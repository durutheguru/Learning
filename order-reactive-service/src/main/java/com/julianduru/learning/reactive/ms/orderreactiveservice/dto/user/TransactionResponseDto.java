package com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user;

import lombok.Data;
import lombok.ToString;

/**
 * created by julian on 21/03/2022
 */
@Data
@ToString
public class TransactionResponseDto {


    private Integer userId;


    private Integer amount;


    private TransactionStatus status;


}
