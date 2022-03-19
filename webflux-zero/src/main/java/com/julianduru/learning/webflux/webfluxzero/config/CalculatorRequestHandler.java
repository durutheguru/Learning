package com.julianduru.learning.webflux.webfluxzero.config;

import com.julianduru.learning.webflux.webfluxzero.dto.Request;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.exception.InputValidationException;
import com.julianduru.learning.webflux.webfluxzero.service.ReactiveCalculatorService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * created by julian on 18/03/2022
 */
@Service
@RequiredArgsConstructor
public class CalculatorRequestHandler {


    private final ReactiveCalculatorService calculatorService;


    private Map<String, Function<Request, Mono<Response>>> operatorMap;


    @PostConstruct
    public void postConstruct() {
        operatorMap = new HashMap<>(
            Map.of(
                "+", calculatorService::add,
                "-", calculatorService::subtract,
                "/", calculatorService::divide,
                "*", calculatorService::multiply
            )
        );
    }


    public Mono<ServerResponse> operate(ServerRequest serverRequest) {
        var headerOp = serverRequest.headers().firstHeader("OP");
        if (Strings.isEmpty(headerOp)) {
            return Mono.error(new IllegalArgumentException("Request must provide a valid operation"));
        }

        var request = Request.builder()
            .first(Integer.valueOf(serverRequest.pathVariable("first")))
            .second(Integer.valueOf(serverRequest.pathVariable("second")))
            .build();

        return ServerResponse.ok().body(
            operatorMap.get(headerOp).apply(request), Response.class
        );
    }



}
