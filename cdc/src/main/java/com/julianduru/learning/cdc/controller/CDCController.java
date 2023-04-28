package com.julianduru.learning.cdc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianduru.learning.cdc.CdcApplication;
import com.julianduru.learning.cdc.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Julian Duru on 25/04/2023
 */
@RestController
@RequestMapping("/api/cdc")
public class CDCController {


    @GetMapping
    public ResponseEntity<String> getPayloads() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");

        return new ResponseEntity<>(
            new ObjectMapper().writeValueAsString(CdcApplication.payloads),
            headers, HttpStatus.OK
        );
    }


}
