package com.julianduru.learning.reactive.ms.productreactiveservice.service;

import com.julianduru.learning.reactive.ms.productreactiveservice.dto.ProductDto;
import com.julianduru.learning.reactive.ms.productreactiveservice.repository.ProductRepository;
import com.julianduru.learning.reactive.ms.productreactiveservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * created by julian on 19/03/2022
 */
@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository repository;


    private final Sinks.Many<ProductDto> sink;


    public Flux<ProductDto> getAll() {
        return repository.findAll().map(EntityDtoUtil::toDto);
    }


    public Mono<ProductDto> getProductById(String id) {
        return repository.findById(id).map(EntityDtoUtil::toDto);
    }


    public Mono<ProductDto> saveProduct(Mono<ProductDto> dto) {
        return dto
            .map(EntityDtoUtil::toEntity)
            .flatMap(this.repository::insert)
            .map(EntityDtoUtil::toDto)
            .doOnNext(this.sink::tryEmitNext);
    }


    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> dto) {
        return this.repository.findById(id)
            .flatMap(p ->
                dto.map(EntityDtoUtil::toEntity)
                    .doOnNext(e -> e.setId(id))
            )
            .flatMap(this.repository::save)
            .map(EntityDtoUtil::toDto);
    }


    public Mono<Void> deleteProduct(String id) {
        return this.repository.deleteById(id);
    }


    public Flux<ProductDto> getProductsPricedBetween(int min, int max) {
        return repository.findByPriceBetween(Range.closed(min, max))
            .map(EntityDtoUtil::toDto);
    }


}

