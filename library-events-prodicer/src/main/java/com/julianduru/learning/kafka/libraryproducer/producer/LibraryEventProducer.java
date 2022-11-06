package com.julianduru.learning.kafka.libraryproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * created by julian on 06/11/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryEventProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;


    public void sendLibraryEvent(LibraryEvent event) throws JsonProcessingException {
        var key = String.valueOf(event.getLibraryEventId());
        var value = objectMapper.writeValueAsString(event);

        var listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(
            new ListenableFutureCallback<>() {

                @Override
                public void onFailure(Throwable ex) {
                    handleFailure(key, value, ex);
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    handleSuccess(key, value, result);
                }
            }
        );
    }


    public void sendLibraryEventRecord(LibraryEvent event) throws JsonProcessingException {
        var key = String.valueOf(event.getLibraryEventId());
        var value = objectMapper.writeValueAsString(event);

        var listenableFuture = kafkaTemplate.send(
            new ProducerRecord<>(
                "library-events", null, key, value,
                List.of(
                    new RecordHeader("event-source", "scanner".getBytes(StandardCharsets.UTF_8))
                )
            )
        );
        listenableFuture.addCallback(
            new ListenableFutureCallback<>() {

                @Override
                public void onFailure(Throwable ex) {
                    handleFailure(key, value, ex);
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    handleSuccess(key, value, result);
                }
            }
        );
    }


    public SendResult<String, String> sendLibraryEventSynchronous(LibraryEvent event) throws JsonProcessingException, ExecutionException, InterruptedException {
        var key = String.valueOf(event.getLibraryEventId());
        var value = objectMapper.writeValueAsString(event);

        try {
            var result = kafkaTemplate.sendDefault(key, value).get(
//                1, TimeUnit.SECONDS
            );
            log.info("SendResult is {}", result.toString());
            return result;
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    private void handleFailure(String key, String value, Throwable ex) {
        log.error("Error sending Message , key: {}, value: {}, Ex: {}", key, value, ex.getMessage());
    }

    private void handleSuccess(String key, String value, SendResult<String, String> result) {
        log.info(
            "Message Sent Successfully for key: {}, value: {}, partition: {}",
            key, value, result.getRecordMetadata().partition()
        );
    }


}
