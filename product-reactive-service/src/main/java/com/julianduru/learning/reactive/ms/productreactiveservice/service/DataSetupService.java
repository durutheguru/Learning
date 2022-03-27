package com.julianduru.learning.reactive.ms.productreactiveservice.service;

import com.julianduru.learning.reactive.ms.productreactiveservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by julian on 20/03/2022
 */
@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {


    private final ProductService service;


    @Override
    public void run(String... args) throws Exception {
        var product1 = new ProductDto("4k-tv", 1000);
        var product2 = new ProductDto("slr-camera", 1000);
        var product3 = new ProductDto("iphone", 1000);
        var product4 = new ProductDto("headphone", 1000);

        Flux.just(product1, product2, product3, product4)
            .flatMap(p -> service.saveProduct(Mono.just(p)))
            .subscribe(System.out::println);
    }


}
