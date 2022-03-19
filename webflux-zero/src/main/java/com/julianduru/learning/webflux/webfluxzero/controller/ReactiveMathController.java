package com.julianduru.learning.webflux.webfluxzero.controller;

import com.julianduru.learning.webflux.webfluxzero.dto.MultiplyRequestDto;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.exception.InputValidationException;
import com.julianduru.learning.webflux.webfluxzero.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * created by julian on 08/03/2022
 */
@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {


    private final ReactiveMathService mathService;


    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }

        return mathService.findSquare(input);
    }


    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }


    @GetMapping(
        value = "table/{input}/stream",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }


    @PostMapping("multiply")
    public Mono<Response> multiply(
        @RequestBody Mono<MultiplyRequestDto> requestDto,
        @RequestHeader Map<String, String> headers
    ) {
        System.out.println(headers);
        return mathService.multiply(requestDto);
    }


    @PostMapping(value = "multiply_flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplyList(@RequestBody Flux<MultiplyRequestDto> requestDtoFlux) {
        return mathService.multiplyFlux(requestDtoFlux);
    }


}


