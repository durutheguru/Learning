package com.julianduru.learning.lambda.crud.models;

import com.julianduru.learning.lambda.crud.util.converter.LocalDateConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * created by julian on 03/02/2023
 */
@Data
@Entity
public class Book extends BaseEntity {


    private String title;


    private String author;


    @Column(nullable = false, unique = true, length = 100)
    private String isbn;


    @Convert(converter = LocalDateConverter.class)
    private LocalDate publishedDate;


    private String description;


    private String thumbnail;


    private String smallThumbnail;


    private String language;


    private String previewLink;


    private String infoLink;


    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


    public Book() {}


    public Book(Long id) {
        this.setId(id);
    }


}
