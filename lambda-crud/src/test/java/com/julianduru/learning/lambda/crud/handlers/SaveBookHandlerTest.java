package com.julianduru.learning.lambda.crud.handlers;

import com.julianduru.learning.lambda.crud.util.LambdaContext;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/02/2023
 */
public class SaveBookHandlerTest {


    @Test
    public void saveBook() {
        var handler = new SaveBookHandler();

        var book = handler.handleRequest(
            Map.of(
                "title", "The Book of Life",
                "author", "J. D. Salinger",
                "isbn", "978-0-553-57340-7",
                "publishedDate", "1951-01-01",
                "description", "The Catcher in the Rye is a 1951 novel by J. D. Salinger. ",
                "thumbnail", "http://books.google.com/books/content?id=5x0EAAAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "smallThumbnail", "http://books.google.com/books/content?id=5x0EAAAAMAAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
                "language", "en",
                "previewLink", "http://books.google.com/books?id=5x0EAAAAMAAJ&dq=isbn:9780553573407&hl=&source=gbs_api",
                "publisherId", "1"
            ),

            new LambdaContext()
        );

        assertThat(book).isNotNull();
    }


}


