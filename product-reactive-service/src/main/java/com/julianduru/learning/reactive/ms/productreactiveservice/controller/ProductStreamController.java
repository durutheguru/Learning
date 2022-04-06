package com.julianduru.learning.reactive.ms.productreactiveservice.controller;

import com.julianduru.learning.reactive.ms.productreactiveservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * created by julian on 27/03/2022
 */
@CrossOrigin
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductStreamController {


    private final Flux<ProductDto> flux;


    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getUpdates() {
        return this.flux;
    }


    @GetMapping(value = "stream/{price}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getUpdates(@PathVariable("price") Integer price) {
        return this.flux.filter(d -> d.getPrice() <= price);
    }



}

