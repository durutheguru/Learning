package com.julianduru.learning.webflux.webfluxzero.service;

import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.util.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * created by julian on 08/03/2022
 */
@Slf4j
@Service
public class MathService {


    public Response findSquare(int input) {
        return new Response(input * input);
    }


    public List<Response> multiplicationTable(int input) {
        return IntStream.range(1, 10)
            .peek(i -> SleepUtil.sleepSeconds(1))
            .peek(i -> log.info("Map Service Processing element: " + i))
            .mapToObj(i -> new Response(i * input))
            .collect(Collectors.toList());
    }


}


