package com.julianduru.learning.webflux.webfluxzero.service;

import com.julianduru.learning.webflux.webfluxzero.dto.MultiplyRequestDto;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.IntStream;

/**
 * created by julian on 08/03/2022
 */
@Slf4j
@Service
public class ReactiveMathService {


    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input).map(Response::new);
    }


    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
            .delayElements(Duration.ofSeconds(1))
            .doOnNext(i -> log.info("Reactive Math Service processing: " + i))
            .map(i -> new Response(i * input));
    }


    public Flux<Response> multiplicationTableMod(int input) {
        var responses = IntStream.range(1, 10)
            .peek(i -> SleepUtil.sleepSeconds(1))
            .peek(i -> log.info("Map Service Processing element: " + i))
            .mapToObj(i -> new Response(i * input))
            .toList();

        return Flux.fromIterable(responses);
    }


    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono) {
        return dtoMono.map(m -> new Response(m.getFirst() * m.getSecond()));
    }


    public Flux<Response> multiplyFlux(Flux<MultiplyRequestDto> dtoFlux) {
        return dtoFlux.map(m -> m.getFirst() * m.getSecond())
            .delayElements(Duration.ofSeconds(2))
            .map(Response::new);
    }


}


