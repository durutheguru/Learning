package com.julianduru.learning.webflux.webfluxzero;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

/**
 * created by julian on 18/03/2022
 */
public class BadRequestTest extends BaseTest {


    @Autowired
    private WebClient webClient;


    @Test
    public void badRequestTest() {
        var responseMono = webClient.get()
            .uri("reactive-math/square/{number}/throw", 5)
            .headers(h -> {
                h.setBasicAuth("username", "password");
                h.set("someKey", "someValue");
                h.set("anotherKey", "anotherValue");
            })
            .retrieve()
            .bodyToMono(Response.class)
            .doOnNext(System.out::println)
            .doOnError(err -> System.err.println("ERR>>: " + err.getMessage()));

        StepVerifier.create(responseMono)
            .verifyError(WebClientResponseException.class);
    }



}
