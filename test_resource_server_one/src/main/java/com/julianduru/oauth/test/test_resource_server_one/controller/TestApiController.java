package com.julianduru.oauth.test.test_resource_server_one.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * created by julian on 25/04/2022
 */
@RestController
@RequestMapping("/api")
public class TestApiController {


    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.fromSupplier(() -> "Hello World!!!");
    }


}

