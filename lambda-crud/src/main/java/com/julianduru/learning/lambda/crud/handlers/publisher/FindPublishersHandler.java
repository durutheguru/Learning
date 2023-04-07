package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.julianduru.learning.lambda.crud.handlers.BaseHandler;
import com.julianduru.learning.lambda.crud.repository.PublisherRepository;
import com.julianduru.learning.lambda.crud.util.JSONUtil;

/**
 * created by julian on 05/02/2023
 */
public class FindPublishersHandler extends BaseHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final PublisherRepository publisherRepository = new PublisherRepository();


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        var publishers = publisherRepository.findAll(hibernateUtil.getSessionFactory());
        var response = new APIGatewayProxyResponseEvent();
        response.setBody(JSONUtil.asJsonString(publishers, ""));

        return response;
    }


}
