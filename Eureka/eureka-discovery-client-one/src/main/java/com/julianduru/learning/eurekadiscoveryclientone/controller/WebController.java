package com.julianduru.learning.eurekadiscoveryclientone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by julian on 25/08/2022
 */
@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello world from Client One!!!";
    }

}
