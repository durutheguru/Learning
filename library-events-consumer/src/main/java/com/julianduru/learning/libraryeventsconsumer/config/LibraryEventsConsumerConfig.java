package com.julianduru.learning.libraryeventsconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

/**
 * created by julian on 10/11/2022
 */
@Slf4j
@EnableKafka
@Configuration
public class LibraryEventsConsumerConfig {

    @Autowired
    private KafkaTemplate kafkaTemplate;


    @Value("${topics.retry}")
    private String retryTopic;


    @Value("${topics.dlt}")
    private String dltTopic;


    public DeadLetterPublishingRecoverer publishingRecoverer() {
        var recoverer = new DeadLetterPublishingRecoverer(
            kafkaTemplate,

            (record, e) -> {
                if (e instanceof IllegalArgumentException) {
                    return new TopicPartition(retryTopic, record.partition());
                }
                else {
                    return new TopicPartition(dltTopic, record.partition());
                }
            }
        );

        return recoverer;
    }


    public DefaultErrorHandler errorHandler() {
        var exceptionsToIgnoreList = List.of(
            IllegalArgumentException.class
        );

        var backoff = new FixedBackOff(1000L, 2);
        var errorHandler = new DefaultErrorHandler(publishingRecoverer(), backoff);

//        exceptionsToIgnoreList.forEach(errorHandler::addRetryableExceptions);
        exceptionsToIgnoreList.forEach(errorHandler::addNotRetryableExceptions);

        errorHandler.setRetryListeners(
            (record, ex, deliveryAttempt) -> {
                log.info("Failed Record in Retry Listener. Exception : {}, deliveryAttempt: {}", ex.getMessage(), deliveryAttempt);
            }
        );

        return errorHandler;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
        ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
        ConsumerFactory<Object, Object> kafkaConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }


}
