package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
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
public class SavePublisherHandler extends BaseHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {


    private final PublisherRepository publisherRepository = new PublisherRepository();



    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Input: " + JSONUtil.asJsonString(input, "--"));

        try {
            var request = JSONUtil.fromJsonString(input.getBody(), CreatePublisherRequest.class);
            var publisher = readPublisherFromInput(request);
            publisher = publisherRepository.save(hibernateUtil.getSessionFactory(), publisher);

            log.info("Successfully Saved This Our Publisher!!!");

            var response = new APIGatewayProxyResponseEvent();
            response.setBody(JSONUtil.asJsonString(publisher));
            return response;
        }
        catch (Throwable t) {
            log.error("Error: " + t.getMessage(), t);
            throw new RuntimeException(t);
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

