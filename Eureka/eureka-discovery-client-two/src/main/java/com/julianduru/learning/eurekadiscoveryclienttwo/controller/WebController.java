package com.julianduru.learning.eurekadiscoveryclienttwo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * created by julian on 25/08/2022
 */
@RestController
@RequestMapping("/web")
public class WebController {


    @Autowired
    private WebClient webClient;


    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world from Client Two!!!";
    }


    @GetMapping("/hello-one")
    public String helloFromClientOne() {
        var response = webClient.get()
            .uri("/web/hello")
            .retrieve()
            .bodyToMono(String.class)
            .blockOptional();

        if (response.isPresent()) {
            return response.get();
        }

        throw new IllegalStateException("No response from client one");
    }


}
