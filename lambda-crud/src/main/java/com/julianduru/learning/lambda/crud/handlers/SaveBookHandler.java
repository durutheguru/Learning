package com.julianduru.learning.lambda.crud.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.julianduru.learning.lambda.crud.dto.CreateBookRequest;
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
public class SaveBookHandler extends BaseHandler<Map<String, Object>, Book> {


    private final BookRepository bookRepository = new BookRepository();


    @Override
    public Book handleRequest(Map<String, Object> input, Context context) {
        var logger = context.getLogger();
        logger.log("Input: " + JSONUtil.asJsonString(input, "--"));

        try {
            var book = readBookFromInput(input);
            return bookRepository.save(book);
        }
        catch (Throwable t) {
            logger.log("Error: " + t.getMessage());
            t.printStackTrace();
            return null;
        }
    }


    private Book readBookFromInput(Map<String, Object> input) throws IOException {
        var createBook = JSONUtil.fromJsonString(
            JSONUtil.asJsonString(input), CreateBookRequest.class
        );

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
