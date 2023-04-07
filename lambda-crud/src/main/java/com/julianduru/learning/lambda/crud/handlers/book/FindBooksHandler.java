package com.julianduru.learning.lambda.crud.handlers.book;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.repository.BookRepository;
import com.julianduru.learning.lambda.crud.util.JSONUtil;

/**
 * created by julian on 05/02/2023
 */
public class FindBooksHandler extends BaseHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    private final BookRepository bookRepository = new BookRepository();


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        var books = bookRepository.findAll(hibernateUtil.getSessionFactory());
        var response = new APIGatewayProxyResponseEvent();
        response.setBody(JSONUtil.asJsonString(books, ""));

        return response;
    }


}

