package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.julianduru.learning.lambda.crud.data.CreatePublisherDataProvider;
import com.julianduru.learning.lambda.crud.handlers.publisher.SavePublisherHandler;
import com.julianduru.learning.lambda.crud.util.LambdaContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/02/2023
 */
public class SavePublisherHandlerTest {


    private CreatePublisherDataProvider dataProvider = new CreatePublisherDataProvider();


    @Test
    public void savePublisher() {
        var handler = new SavePublisherHandler();


        var publisher = handler.handleRequest(
            dataProvider.provide(), new LambdaContext()
        );

        assertThat(publisher).isNotNull();
    }


}
