package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.amazonaws.services.lambda.runtime.Context;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.models.Publisher;
import com.julianduru.learning.lambda.crud.repository.PublisherRepository;

import java.util.List;
import java.util.Map;

/**
 * created by julian on 05/02/2023
 */
public class FindPublishersHandler extends BaseHandler<Map<String, String>, List<Publisher>> {

    private final PublisherRepository publisherRepository = new PublisherRepository();


    @Override
    public List<Publisher> handleRequest(Map<String, String> input, Context context) {
        return publisherRepository.findAll(hibernateUtil.getSessionFactory());
    }


}
