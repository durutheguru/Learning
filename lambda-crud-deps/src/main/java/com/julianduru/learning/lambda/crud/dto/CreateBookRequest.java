package com.julianduru.learning.lambda.crud.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * created by julian on 03/02/2023
 */
@Data
public class CreateBookRequest {


    private String title;


    private String author;


    private String isbn;


    private LocalDate publishedDate;


    private String description;


    private String thumbnail;


    private String smallThumbnail;


    private String language;


    private String previewLink;


    private String infoLink;


    private Long publisherId;


}


