package com.julianduru.learning.kafka.libraryproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by julian on 06/11/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {


    private Integer bookId;


    private String bookName;


    private String bookAuthor;


}
