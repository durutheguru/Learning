package com.julianduru.learning.webflux.webfluxzero.controller;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * created by julian on 08/03/2022
 */
@RestController
@RequestMapping("math")
@RequiredArgsConstructor
public class MathController {


    private final MathService mathService;


    @GetMapping("square/{input}")
    public Response findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }


    @GetMapping("table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }



}
