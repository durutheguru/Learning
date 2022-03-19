package com.julianduru.learning.webflux.webfluxzero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

/**
 * created by julian on 18/03/2022
 */
@Configuration
public class WebClientConfig {


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .filter(this::sessionToken)
            .build();
    }


//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction function) {
//        System.out.println("Generating Session Token...");
//
//        var clientRequest = ClientRequest.from(request)
//            .headers(h -> h.setBearerAuth("some-lengthy-jwt"))
//            .build();
//
//        return function.exchange(clientRequest);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction function) {
        var clientRequest = request.attribute("auth")
            .map(v -> v.equals("basic") ? withBasicAuthentication(request) : withBearerAuthentication(request))
            .orElse(request);

        return function.exchange(clientRequest);
    }


    private ClientRequest withBasicAuthentication(ClientRequest request) {
        return ClientRequest.from(request)
            .headers(h -> h.setBasicAuth("username", "password"))
            .build();
    }


    private ClientRequest withBearerAuthentication(ClientRequest request) {
        return ClientRequest.from(request)
            .headers(h -> h.setBearerAuth("jwt-token"))
            .build();
    }


}
