package com.julianduru.learning.reactive.ms.orderreactiveservice.client;

import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.product.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by julian on 23/03/2022
 */
@Service
public class ProductClient {


    private final WebClient webClient;


    public ProductClient(@Value("${product.service.url}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .build();
    }


    public Mono<ProductDto> getProductById(final String productId) {
        return this.webClient
            .get()
            .uri("{id}", productId)
            .retrieve()
            .bodyToMono(ProductDto.class);
    }


    public Flux<ProductDto> getProducts() {
        return this.webClient
            .get()
            .retrieve()
            .bodyToFlux(ProductDto.class);
    }


}
