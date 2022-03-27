package com.julianduru.learning.reactive.ms.userreactiveservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * created by julian on 21/03/2022
 */
@Data
@ToString
public class UserTransaction {

    @Id
    private Integer id;

    private Integer userId;

    private Integer amount;

    private LocalDateTime transactionDate;

}

