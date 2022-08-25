package com.julianduru.learning.springsecuritybasic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by julian on 04/06/2022
 */
@RestController
public class WelcomeController {


    @GetMapping("/welcome")
    public String sayWelcome() {
        return "Welcome from Spring Application with Security.";
    }


    @GetMapping(value = "/username")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


}



