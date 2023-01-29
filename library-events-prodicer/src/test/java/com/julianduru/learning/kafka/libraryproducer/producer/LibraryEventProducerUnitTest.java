package com.julianduru.learning.kafka.libraryproducer.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianduru.learning.kafka.libraryproducer.domain.Book;
import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * created by julian on 07/11/2022
 */
@ExtendWith(MockitoExtension.class)
public class LibraryEventProducerUnitTest {

    @Mock
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper;

    @InjectMocks
    LibraryEventProducer eventProducer;


    @Test
    public void sendLibraryEventFailureTest() throws Exception {
        //pre-condition

        //action
        eventProducer.sendLibraryEventRecord(
            LibraryEvent.builder()
                .libraryEventId(null)
                .book(
                    Book.builder()
                        .bookId(123)
                        .bookAuthor("Henry George")
                        .bookName("Rage on the Night")
                        .build()
                )
                .build()
        );

        //validation
    }


}

