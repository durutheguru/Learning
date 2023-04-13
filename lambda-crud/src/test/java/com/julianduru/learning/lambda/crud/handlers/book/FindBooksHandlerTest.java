package com.julianduru.learning.lambda.crud.handlers.book;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 05/02/2023
 */
public class FindBooksHandlerTest {


    @Test
    public void findBooks() {
        var handler = new FindBooksHandler();
        var books = handler.handleRequest(null, null);

//        assertThat(books).isNotEmpty();
    }


}
