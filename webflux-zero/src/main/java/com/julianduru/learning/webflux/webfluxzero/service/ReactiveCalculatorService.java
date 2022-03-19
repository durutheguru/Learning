package com.julianduru.learning.webflux.webfluxzero.service;

import com.julianduru.learning.webflux.webfluxzero.dto.Request;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * created by julian on 18/03/2022
 */
@Service
public class ReactiveCalculatorService {


    public Mono<Response> add(Request request) {
        return Mono.fromSupplier(() -> new Response(request.getFirst() + request.getSecond()));
    }


    public Mono<Response> subtract(Request request) {
        return Mono.fromSupplier(() -> new Response(request.getFirst() - request.getSecond()));
    }


    public Mono<Response> divide(Request request) {
        return Mono.fromSupplier(() -> new Response(request.getFirst() / request.getSecond()));
    }


    public Mono<Response> multiply(Request request) {
        return Mono.fromSupplier(() -> new Response(request.getFirst() * request.getSecond()));
    }


}


