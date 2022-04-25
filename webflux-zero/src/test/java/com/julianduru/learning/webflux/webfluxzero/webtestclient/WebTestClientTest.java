package com.julianduru.learning.webflux.webfluxzero.webtestclient;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 06/04/2022
 */
@SpringBootTest
@AutoConfigureWebTestClient
public class WebTestClientTest {


    @Autowired
    private WebTestClient webClient;


    @Test
    public void stepVerifierTest() {
        var responseMono = webClient.get()
            .uri("/reactive-math/square/{number}", 15)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .returnResult(Response.class)
            .getResponseBody();

        StepVerifier.create(responseMono)
            .expectNextMatches(r -> r.getOutput() == 225)
            .verifyComplete();
    }


    @Test
    public void fluentAssertionTest() {
        webClient.get()
            .uri("/reactive-math/square/{number}", 15)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Response.class)
            .value(r -> {
                assertThat(r.getOutput()).isEqualTo(225);
            });
    }


}
