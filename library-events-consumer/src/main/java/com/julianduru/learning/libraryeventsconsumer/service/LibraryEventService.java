package com.julianduru.learning.libraryeventsconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianduru.learning.libraryeventsconsumer.entity.LibraryEvent;
import com.julianduru.learning.libraryeventsconsumer.jpa.LibraryEventsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by julian on 15/11/2022
 */
@Slf4j
@Service
public class LibraryEventService {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LibraryEventsRepository eventsRepository;


    public void processLibraryEvent(ConsumerRecord<String, String> record) throws JsonProcessingException {
        var event = objectMapper.readValue(record.value(), LibraryEvent.class);
        log.info("Library Event: {}", event);

        switch (event.getEventType()) {
            case NEW:
                //save operation;
                save(event);
                break;
            case UPDATE:
                //update operation
                validate(event);
                save(event);
                break;
            default:
                log.info("Invalid Library Event Type: {}", event.getEventType());
        }
    }

    private void validate(LibraryEvent event) {
        if (event.getLibraryEventId() == null) {
            throw new IllegalArgumentException("Library Event ID is missing.");
        }

        var eventOptional = eventsRepository.findById(event.getLibraryEventId());
        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Library Event ID not valid.");
        }
        log.info("Validation is successful for the Library Event. {}", eventOptional.get());
    }


    private void save(LibraryEvent event) {
        event.getBook().setLibraryEvent(event);
        eventsRepository.save(event);

        log.info("Successfully persisted library event {}", event);
    }


}
