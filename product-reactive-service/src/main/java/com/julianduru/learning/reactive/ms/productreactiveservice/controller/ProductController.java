package com.julianduru.learning.reactive.ms.productreactiveservice.controller;

import com.julianduru.learning.reactive.ms.productreactiveservice.dto.ProductDto;
import com.julianduru.learning.reactive.ms.productreactiveservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

/**
 * created by julian on 19/03/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.PATH)
public class ProductController {

    public static final String PATH = "/product";


    private final ProductService service;


    @GetMapping
    public Flux<ProductDto> getAll() {
        return service.getAll();
    }


    @GetMapping("/priced")
    public Flux<ProductDto> getAllPricedBetween(
        @RequestParam("min") Integer min,
        @RequestParam("max") Integer max
    ) {
        return service.getProductsPricedBetween(min, max);
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable String id) {
        simulateRandomException();
        return service.getProductById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> dto) {
        return service.saveProduct(dto);
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(
        @PathVariable String id, @RequestBody Mono<ProductDto> dto
    ) {
        return service.updateProduct(id, dto)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return service.deleteProduct(id);
    }


    private void simulateRandomException() {
        var nextInt = ThreadLocalRandom.current().nextInt(1, 10);
        if (nextInt > 5) {
            throw new RuntimeException("Something is wrong");
        }
    }


}



