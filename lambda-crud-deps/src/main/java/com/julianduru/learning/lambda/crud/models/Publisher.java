package com.julianduru.learning.lambda.crud.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

/**
 * created by julian on 03/02/2023
 */
@Data
@Entity
public class Publisher extends BaseEntity {


    @Column(nullable = false, unique = true, length = 200)
    private String name;


    private String address;


    private String city;


    private String state;


    private String zip;


    private String country;


    private String phone;


    private String fax;


    @Column(nullable = false, unique = true, length = 100)
    private String email;


    private String website;


    private String logo;


    private String description;


    private String notes;


    private String createdBy;


    private String updatedBy;


    public Publisher() {}


    public Publisher(Long id) {
        this.setId(id);
    }



}

