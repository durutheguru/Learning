package com.julianduru.learning.lambda.crud.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.julianduru.learning.lambda.crud.dto.CreatePublisherRequest;
import com.julianduru.learning.lambda.crud.models.Publisher;
import com.julianduru.learning.lambda.crud.repository.PublisherRepository;
import com.julianduru.learning.lambda.crud.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * created by julian on 03/02/2023
 */
@Slf4j
public class SavePublisherHandler extends BaseHandler<Map<String, Object>, Publisher> {


    private final PublisherRepository publisherRepository = new PublisherRepository();



    @Override
    public Publisher handleRequest(Map<String, Object> input, Context context) {
        var logger = context.getLogger();
        System.out.println("Input: " + JSONUtil.asJsonString(input, "--"));
//        logger.log("Input: " + JSONUtil.asJsonString(input, "--"));

        try {
            var publisher = readPublisherFromInput(input);
            return publisherRepository.save(publisher);
        }
        catch (Throwable t) {
//            logger.log("Error: " + t.getMessage());
            t.printStackTrace();
            return null;
        }
    }


    private Publisher readPublisherFromInput(Map<String, Object> input) throws IOException {
        var createPublisher = JSONUtil.fromJsonString(
            JSONUtil.asJsonString(input), CreatePublisherRequest.class
        );

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


