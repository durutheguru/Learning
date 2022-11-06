package com.julianduru.learning.kafka.libraryproducer.controller;

import com.julianduru.learning.kafka.libraryproducer.domain.Book;
import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * created by julian on 06/11/2022
 */
//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
    topics = {
        "library-events"
    },
    partitions = 3
)
@TestPropertySource(
    properties = {
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}"
    }
)
public class LibraryEventsControllerIntegrationTest {


    @Autowired
    TestRestTemplate testRestTemplate;


    @Autowired
    EmbeddedKafkaBroker kafkaBroker;


    private Consumer<String, String> consumer;


    @BeforeEach
    void setUp() {
        consumer = new DefaultKafkaConsumerFactory<>(
            new HashMap<>(
                KafkaTestUtils.consumerProps(
                    "group1", "true", kafkaBroker
                )
            ),
            new StringDeserializer(), new StringDeserializer()
        ).createConsumer();

        kafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }


    @Test
    @Timeout(3)
    void postLibraryEvent() {
        var response = testRestTemplate.exchange(
            "/v1/libraryevent", HttpMethod.POST,
            new HttpEntity<>(
                LibraryEvent.builder()
                    .libraryEventId(null)
                    .book(
                        Book.builder()
                            .bookId(123)
                            .bookAuthor("Henry George")
                            .bookName("Rage on the Night")
                            .build()
                    )
                    .build(),

                new MultiValueMapAdapter<>(
                    Map.of("content-type", List.of(MediaType.APPLICATION_JSON_VALUE))
                )
            ),

            LibraryEvent.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var consumerRecord = KafkaTestUtils.getSingleRecord(consumer, "library-events");

        assertEquals(
        """
                {"libraryEventId":null,"eventType":"NEW","book":{"bookId":123,"bookName":"Rage on the Night","bookAuthor":"Henry George"}}
                """.trim(),
            consumerRecord.value()
        );
    }


    @AfterEach
    void tearDown() {
        consumer.close();
    }


}
