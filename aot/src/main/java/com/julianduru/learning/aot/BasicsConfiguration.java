package com.julianduru.learning.aot;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NativeDetector;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

/**
 * created by Julian Duru on 08/05/2023
 */
@Configuration
class BasicsConfiguration {

    @Bean
    ApplicationListener<ApplicationReadyEvent> basicsApplicationListener(CustomerRepository repository) {
        return event -> repository
            .saveAll(Stream.of("A", "B", "C").map(name -> new Customer(null, name)).toList())
            .forEach(System.out::println);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> detectionListener() {
        return args -> System.out.println("is native image? " + NativeDetector.inNativeImage());
    }

}


record Customer(@Id Integer id, String name) {
}


interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
