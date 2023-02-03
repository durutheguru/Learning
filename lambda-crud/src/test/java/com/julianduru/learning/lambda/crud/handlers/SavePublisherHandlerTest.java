package com.julianduru.learning.lambda.crud.handlers;

import com.julianduru.learning.lambda.crud.util.LambdaContext;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/02/2023
 */
public class SavePublisherHandlerTest {


    @Test
    public void savePublisher() {
        var handler = new SavePublisherHandler();


        var publisher = handler.handleRequest(
            Map.of(
                "name", "Penguin Books",
                "address", "80 Strand, London WC2R 0RL, United Kingdom",
                "phone", "+44 20 7925 6000",
                "website", "http://www.penguin.co.uk/",
                "logo", "http://books.google.com/books/content?id=5x0EAAAAMAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
                "description", "Penguin Books is a British publishing house. It was founded in 1935 by Sir Allen Lane as a line of the publishers",
                "email", "email@gmail.com"
            ),

            new LambdaContext()
        );

        assertThat(publisher).isNotNull();
    }


}
