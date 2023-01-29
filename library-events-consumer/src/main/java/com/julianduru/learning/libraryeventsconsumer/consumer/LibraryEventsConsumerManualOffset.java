package com.julianduru.learning.libraryeventsconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * created by julian on 10/11/2022
 */
@Slf4j
//@Component
public class LibraryEventsConsumerManualOffset implements AcknowledgingMessageListener<String, String> {


    @Override
    @KafkaListener(
        topics = {"library-events"}
    )
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("Consumer Record: {}", record);
        acknowledgment.acknowledge();
    }


}
