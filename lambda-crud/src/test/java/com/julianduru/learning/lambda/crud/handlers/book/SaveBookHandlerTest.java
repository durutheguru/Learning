package com.julianduru.learning.lambda.crud.handlers.book;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.julianduru.learning.lambda.crud.data.CreateBookDataProvider;
import com.julianduru.learning.lambda.crud.util.JSONUtil;
import com.julianduru.learning.lambda.crud.util.LambdaContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/02/2023
 */
public class SaveBookHandlerTest {


    private CreateBookDataProvider dataProvider = new CreateBookDataProvider();


    @Test
    public void saveBook() throws Exception {
        var handler = new SaveBookHandler();
        var input = new APIGatewayProxyRequestEvent();
        input.setBody(JSONUtil.asJsonString(dataProvider.provide()));

        var response = handler.handleRequest(
            input, new LambdaContext()
        );

        assertThat(response).isNotNull();
    }


}


