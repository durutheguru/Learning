package com.julianduru.learning.webflux.webfluxzero.controller;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.exception.InputValidationException;
import com.julianduru.learning.webflux.webfluxzero.service.ReactiveMathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * created by julian on 15/03/2022
 */
@RestController
@RequestMapping("reactive-math")
public record ReactiveMathValidationController(
    ReactiveMathService mathService
) {


    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }

        return mathService.findSquare(input);
    }


    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
            .handle((i , sink) -> {
                if (i >= 10 && i <= 20) {
                    sink.next(i);
                }
                else {
                    sink.error(new InputValidationException(i));
                }
            })
            .cast(Integer.class)
            .flatMap(mathService::findSquare);
    }


}
