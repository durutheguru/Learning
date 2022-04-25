package com.julianduru.learning.webflux.webfluxzero.webclient;

import com.julianduru.learning.webflux.webfluxzero.dto.MultiplyRequestDto;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

/**
 * created by julian on 18/03/2022
 */
public class HeadersTest extends BaseTest {


    @Autowired
    private WebClient webClient;


    @Test
    public void headersTest() {
        var responseMono = webClient.post()
            .uri("reactive-math/multiply")
            .bodyValue(buildRequestDto(5, 2))
            .headers(h -> {
                h.set("someKey", "someValue");
                h.set("anotherKey", "anotherValue");
            })
            .retrieve()
            .bodyToMono(Response.class)
            .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
            .expectNextCount(1)
            .verifyComplete();
    }


    private MultiplyRequestDto buildRequestDto(int a, int b) {
        var dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);

        return dto;
    }



}
