package com.julianduru.learning.reactive.ms.userreactiveservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * created by julian on 21/03/2022
 */
@Data
@ToString
@Table("users")
public class User {

    @Id
    private Integer id;

    private String name;

    private Integer balance;

}

