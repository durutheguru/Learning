package com.julianduru.learning.libraryeventsconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.julianduru.learning.libraryeventsconsumer.service.LibraryEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * created by julian on 10/11/2022
 */
@Slf4j
@Component
public class LibraryEventsConsumer {


    @Autowired
    private LibraryEventService libraryEventService;


    @KafkaListener(
        topics = {"library-events"}
    )
    public void onMessage(ConsumerRecord<String, String> record) throws JsonProcessingException {
        log.info("Consumer Record: {}", record);
        libraryEventService.processLibraryEvent(record);
    }


}
