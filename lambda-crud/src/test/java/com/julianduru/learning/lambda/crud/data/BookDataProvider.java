package com.julianduru.learning.lambda.crud.data;

import com.julianduru.learning.lambda.crud.models.Book;
import lombok.RequiredArgsConstructor;

/**
 * created by julian on 03/02/2023
 */
@RequiredArgsConstructor
public class BookDataProvider implements DataProvider<Book> {

    private final PublisherDataProvider publisherDataProvider;


    @Override
    public Book provide() {
        var book = new Book();

//        book.setPublisher(publisherDataProvider.provide());
        book.setTitle(faker.book().title());
        book.setDescription(faker.lorem().sentence());

        return book;
    }


}
