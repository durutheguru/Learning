package com.julianduru.learning.libraryeventsconsumer.consumer;

import com.julianduru.learning.libraryeventsconsumer.service.LibraryEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;

/**
 * created by julian on 15/11/2022
 */
@SpringBootTest
@EmbeddedKafka(
    topics = {
        "library-events"
    },
    partitions = 3
)
@TestPropertySource(
    properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"
    }
)
public class LibraryEventsConsumerTest {

    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;


    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    KafkaListenerEndpointRegistry endpointRegistry;


    @SpyBean
    LibraryEventsConsumer libraryEventsConsumerSpy;


    @SpyBean
    LibraryEventService libraryEventServiceSpy;


    @BeforeEach
    public void setup() {
        /**
         * ensures that consumer is assigned necessary partitions before integration test starts..
         */
        for (MessageListenerContainer container: endpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(container, kafkaBroker.getPartitionsPerTopic());
        }
    }


    @Test
    void publishNewLibraryEvent() {
        //pre-condition


        //action

        //validation
    }
}
