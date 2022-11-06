package com.julianduru.learning.kafka.libraryproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEvent;
import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEventType;
import com.julianduru.learning.kafka.libraryproducer.producer.LibraryEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * created by julian on 06/11/2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LibraryEventsController {


    private final LibraryEventProducer eventProducer;


    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(
        @RequestBody LibraryEvent event
    ) {
        try {
            log.info("Received Event: {}", event);
            event.setEventType(LibraryEventType.NEW);
            eventProducer.sendLibraryEventRecord(event);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(event);
        }
        catch (JsonProcessingException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }


}
