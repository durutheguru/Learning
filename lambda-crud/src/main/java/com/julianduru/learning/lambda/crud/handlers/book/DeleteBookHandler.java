package com.julianduru.learning.lambda.crud.handlers.book;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.models.Book;
import com.julianduru.learning.lambda.crud.repository.BookRepository;

/**
 * created by julian on 05/02/2023
 */
public class DeleteBookHandler extends BaseHandler<APIGatewayProxyRequestEvent, Book> {

    private final BookRepository bookRepository = new BookRepository();


    @Override
    public Book handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        var id = input.getPathParameters().get("id");
        if (id == null) {
            return null;
        }

        var idLong = Long.parseLong(id);
        return bookRepository.delete(new Book(idLong));
    }


}
