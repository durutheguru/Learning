package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.amazonaws.services.lambda.runtime.Context;
import com.julianduru.learning.lambda.crud.dto.CreatePublisherRequest;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.models.Publisher;
import com.julianduru.learning.lambda.crud.repository.PublisherRepository;
import com.julianduru.learning.lambda.crud.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * created by julian on 03/02/2023
 */
@Slf4j
public class SavePublisherHandler extends BaseHandler<CreatePublisherRequest, Publisher> {


    private final PublisherRepository publisherRepository = new PublisherRepository();



    @Override
    public Publisher handleRequest(CreatePublisherRequest input, Context context) {
        System.out.println("Input: " + JSONUtil.asJsonString(input, "--"));
        log.info("Input: " + JSONUtil.asJsonString(input, "--"));

        try {
            var publisher = readPublisherFromInput(input);
            return publisherRepository.save(hibernateUtil.getSessionFactory(), publisher);
        }
        catch (Throwable t) {
            log.error("Error: " + t.getMessage(), t);
            return null;
        }
    }


    private Publisher readPublisherFromInput(CreatePublisherRequest createPublisher)  {
        var publisher = new Publisher();

        publisher.setName(createPublisher.getName());
        publisher.setAddress(createPublisher.getAddress());
        publisher.setCity(createPublisher.getCity());
        publisher.setCountry(createPublisher.getCountry());
        publisher.setPhone(createPublisher.getPhone());
        publisher.setEmail(createPublisher.getEmail());

        return publisher;
    }


}

