package com.julianduru.learning.webflux.webfluxzero.webclient;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

/**
 * created by julian on 18/03/2022
 */
public class GetMultiResponseTest extends BaseTest {


    @Autowired
    private WebClient webClient;


    @Test
    public void fluxTest() {
        var responseFlux = webClient.get()
            .uri("reactive-math/table/{number}", 5)
            .retrieve()
            .bodyToFlux(Response.class)
            .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
            .expectNextCount(10)
            .verifyComplete();
    }


    @Test
    public void fluxStreamTest() {
        var responseFlux = webClient.get()
            .uri("reactive-math/table/{number}/stream", 5)
            .retrieve()
            .bodyToFlux(Response.class)
            .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
            .expectNextCount(10)
            .verifyComplete();
    }

}
