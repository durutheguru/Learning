package com.julianduru.learning.lambda.crud.handlers.book;

import com.amazonaws.services.lambda.runtime.Context;
import com.julianduru.learning.lambda.crud.dto.CreateBookRequest;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.models.Book;
import com.julianduru.learning.lambda.crud.models.Publisher;
import com.julianduru.learning.lambda.crud.repository.BookRepository;
import com.julianduru.learning.lambda.crud.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * created by julian on 03/02/2023
 */
@Slf4j
public class SaveBookHandler extends BaseHandler<CreateBookRequest, Book> {


    private final BookRepository bookRepository = new BookRepository();


    @Override
    public Book handleRequest(CreateBookRequest input, Context context) {
        log.info("Input: " + JSONUtil.asJsonString(input, "--"));

        try {
            var book = readBookFromInput(input);
            return bookRepository.save(hibernateUtil.getSessionFactory(), book);
        }
        catch (Throwable t) {
            log.error("Error: " + t.getMessage(), t);
            return null;
        }
    }


    private Book readBookFromInput(CreateBookRequest createBook) throws IOException {
        var book = new Book();

        book.setTitle(createBook.getTitle());
        book.setAuthor(createBook.getAuthor());
        book.setIsbn(createBook.getIsbn());
        book.setPublishedDate(createBook.getPublishedDate());
        book.setDescription(createBook.getDescription());
        book.setThumbnail(createBook.getThumbnail());
        book.setSmallThumbnail(createBook.getSmallThumbnail());
        book.setLanguage(createBook.getLanguage());
        book.setPreviewLink(createBook.getPreviewLink());
        book.setInfoLink(createBook.getInfoLink());
        book.setPublisher(new Publisher(createBook.getPublisherId()));

        return book;
    }


}

