package com.julianduru.learning.reactive.ms.productreactiveservice.config;

import com.julianduru.learning.reactive.ms.productreactiveservice.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * created by julian on 27/03/2022
 */
@Configuration
public class SinkConfig {


    @Bean
    public Sinks.Many<ProductDto> sink() {
        return Sinks.many().replay().limit(1);
    }


    @Bean
    public Flux<ProductDto> productBroadcast(Sinks.Many<ProductDto> sink) {
        return sink.asFlux();
    }


}
