package com.julianduru.learning.webflux.webfluxzero.config;

import com.julianduru.learning.webflux.webfluxzero.dto.InputFailedValidationResponse;
import com.julianduru.learning.webflux.webfluxzero.dto.MultiplyRequestDto;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.exception.InputValidationException;
import com.julianduru.learning.webflux.webfluxzero.service.ReactiveMathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * created by julian on 18/03/2022
 */
@Service
public class RequestHandler {


    @Autowired
    private ReactiveMathService mathService;



    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        var number = Integer.valueOf(request.pathVariable("input"));
        var responseMono = mathService.findSquare(number);
        return ServerResponse.ok().body(responseMono, Response.class);
    }


    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        var number = Integer.valueOf(request.pathVariable("input"));
        var responseFlux = mathService.multiplicationTable(number);
        return ServerResponse.ok().body(responseFlux, Response.class);
    }


    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        var number = Integer.valueOf(request.pathVariable("input"));
        var responseFlux = mathService.multiplicationTable(number);
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(responseFlux, Response.class);
    }


    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        var requestMono = request.bodyToMono(MultiplyRequestDto.class);
        var responseMono = mathService.multiply(requestMono);
        return ServerResponse.ok()
            .body(responseMono, Response.class);
    }


    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request) {
        var number = Integer.valueOf(request.pathVariable("input"));
        if (number < 10 || number > 20) {
            return Mono.error(new InputValidationException(number));
        }

        var responseMono = mathService.findSquare(number);
        return ServerResponse.ok().body(responseMono, Response.class);
    }


}


