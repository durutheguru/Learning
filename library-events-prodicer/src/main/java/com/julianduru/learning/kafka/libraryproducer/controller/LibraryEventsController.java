package com.julianduru.learning.kafka.libraryproducer.controller;

import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by julian on 06/11/2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LibraryEventsController {


    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(
        @RequestBody LibraryEvent event
    ) {
        log.info("Received Event: {}", event);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(event);
    }


}
