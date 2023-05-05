package com.julianduru.learning.integration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Instant;

@SpringBootApplication
public class IntegrationApplication {


    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }


	@Bean
	MessageChannel greetings() {
		return MessageChannels.direct().get();
	}



    @Bean
    ApplicationRunner runner() {
        return args -> {
            for (var i = 0; i < 10; i++)
                greetings().send(
                    MessageBuilder
                        .withPayload(text())
                        .build()
                );
        };
    }


    private String text() {
        return Math.random() > .5 ? "Hello World!! @ " + Instant.now() : "holla todo el mundo @ " + Instant.now();
    }


    @Bean
    IntegrationFlow integrationFlow2() {
        return IntegrationFlow
            .from(greetings())
            .filter(String.class, source -> source.contains("holla"))
            .transform(
                (GenericTransformer<String, String>) String::toUpperCase
            )
            .handle((GenericHandler<String>) (payload, headers) -> {
                System.out.println("Here's the payload: " + payload);
                return null;
            })
            .get();
    }


}

