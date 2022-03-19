package com.julianduru.learning.webflux.webfluxzero.config;

import com.julianduru.learning.webflux.webfluxzero.dto.InputFailedValidationResponse;
import com.julianduru.learning.webflux.webfluxzero.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

/**
 * created by julian on 18/03/2022
 */
@Configuration
public class RouterConfig {


    @Autowired
    private RequestHandler requestHandler;


    @Autowired
    private CalculatorRequestHandler calculatorRequestHandler;


    @Bean
    public RouterFunction<ServerResponse> highLevelRouterFunction() {
        return RouterFunctions.route()
            .path("router", this::serverResponseRouterFunction)
            .GET("calculator/{first}/{second}", calculatorRequestHandler::operate)
            .onError(Throwable.class, generalExceptionHandler())
            .build();
    }


    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
            .GET("square/{input}", RequestPredicates.path("*/1?"), requestHandler::squareHandler)
            .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("Only 10-19 allowed"))
            .GET("table/{input}", requestHandler::tableHandler)
            .GET("table/{input}/stream", requestHandler::tableStreamHandler)
            .POST("multiply", requestHandler::multiplyHandler)
            .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
            .onError(InputValidationException.class, inputFailedValidationExceptionHandler())
            .build();
    }



    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> inputFailedValidationExceptionHandler() {
        return (err, req) -> {
            var ex = (InputValidationException) err;

            var response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(ex.getErrorCode());

            return ServerResponse.badRequest().bodyValue(response);
        };
    }


    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> generalExceptionHandler() {
        return (err, req) -> ServerResponse.badRequest().bodyValue(err.getMessage());
    }


}


