package com.julianduru.learning.webflux.webfluxzero;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

/**
 * created by julian on 18/03/2022
 */
public class GetSingleResponseTest extends BaseTest {


    @Autowired
    private WebClient webClient;


    @Test
    public void blockTest() {
        var response = webClient.get()
            .uri("reactive-math/square/{number}", 15)
            .retrieve()
            .bodyToMono(Response.class)
            .block();

        System.out.println("Response: " + response);
    }


    @Test
    public void stepVerifierTest() {
        var responseMono = webClient.get()
            .uri("reactive-math/square/{number}", 15)
            .retrieve()
            .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
            .expectNextMatches(r -> r.getOutput() == 225)
            .verifyComplete();
    }

}

