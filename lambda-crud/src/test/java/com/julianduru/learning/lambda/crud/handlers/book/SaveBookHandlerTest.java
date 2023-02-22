package com.julianduru.learning.lambda.crud.handlers.book;

import com.julianduru.learning.lambda.crud.data.CreateBookDataProvider;
import com.julianduru.learning.lambda.crud.handlers.book.SaveBookHandler;
import com.julianduru.learning.lambda.crud.util.LambdaContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/02/2023
 */
public class SaveBookHandlerTest {


    private CreateBookDataProvider dataProvider = new CreateBookDataProvider();


    @Test
    public void saveBook() {
        var handler = new SaveBookHandler();

        var book = handler.handleRequest(
            dataProvider.provide(), new LambdaContext()
        );

        assertThat(book).isNotNull();
    }


}


