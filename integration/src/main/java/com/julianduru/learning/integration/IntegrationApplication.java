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
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@SpringBootApplication
public class IntegrationApplication {


    public static void main(String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }


    @Component
    static class MyMessageSource implements MessageSource<String> {

        @Override
        public Message<String> receive() {
            return MessageBuilder.withPayload(text()).build();
        }

    }


	@Bean
	MessageChannel greetings() {
		return MessageChannels.direct().get();
	}


    @Bean
    ApplicationRunner runner(IntegrationFlowContext context) {
        return args -> {
//            for (var i = 0; i < 10; i++) {
//                greetings().send(
//                    MessageBuilder
//                        .withPayload(text())
//                        .build()
//                );
//                Thread.sleep(2000L);
//            }
            
        };
    }


    private static String text() {
        return Math.random() > .5 ? "Hello World!! @ " + Instant.now() : "holla todo el mundo @ " + Instant.now();
    }





    @Bean
    IntegrationFlow integrationFlow(MyMessageSource myMessageSource, int seconds, String filterText) {
        return IntegrationFlow
            .from(myMessageSource, p -> p.poller(spec -> spec.fixedDelay(1000L)))
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

