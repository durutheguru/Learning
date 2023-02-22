package com.julianduru.learning.lambda.crud.handlers.book;

import com.amazonaws.services.lambda.runtime.Context;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.models.Book;
import com.julianduru.learning.lambda.crud.repository.BookRepository;

import java.util.List;
import java.util.Map;

/**
 * created by julian on 05/02/2023
 */
public class FindBooksHandler extends BaseHandler<Map<String, String>, List<Book>> {


    private final BookRepository bookRepository = new BookRepository();


    @Override
    public List<Book> handleRequest(Map<String, String> input, Context context) {
        return bookRepository.findAll();
    }



}
