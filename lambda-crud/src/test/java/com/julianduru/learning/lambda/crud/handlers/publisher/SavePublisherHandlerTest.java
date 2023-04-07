package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.julianduru.learning.lambda.crud.data.CreatePublisherDataProvider;
import com.julianduru.learning.lambda.crud.util.JSONUtil;
import com.julianduru.learning.lambda.crud.util.LambdaContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/02/2023
 */
public class SavePublisherHandlerTest {


    private CreatePublisherDataProvider dataProvider = new CreatePublisherDataProvider();


    @Test
    public void savePublisher() throws Exception {
        var handler = new SavePublisherHandler();
        var input = new APIGatewayProxyRequestEvent();
        input.setBody(JSONUtil.asJsonString(dataProvider.provide()));

        var publisher = handler.handleRequest(input, new LambdaContext());

        assertThat(publisher).isNotNull();
    }


}
