package com.julianduru.learning.lambda.crud.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * created by julian on 03/02/2023
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private ZonedDateTime createdDate;


    private ZonedDateTime updatedDate;


    @PrePersist
    public void prePersist() {
        createdDate = ZonedDateTime.now();
    }


    @PreUpdate
    public void preUpdate() {
        updatedDate = ZonedDateTime.now();
    }


}
