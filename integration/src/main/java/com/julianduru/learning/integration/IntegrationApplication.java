package com.julianduru.learning.integration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

@IntegrationComponentScan
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
	MessageChannel greetingsRequests() {
		return MessageChannels.direct().get();
	}



    @Bean
    MessageChannel greetingsResults() {
        return MessageChannels.direct().get();
    }


//    @Bean
//    ApplicationRunner runner(MyMessageSource myMessageSource, IntegrationFlowContext context) {
//        return args -> {
//            var hollaFlow = buildFlow(myMessageSource, 1, "holla");
//            var helloFlow = buildFlow(myMessageSource, 2, "hello");
//
//            Set.of(hollaFlow, helloFlow)
//                .forEach(flow -> context.registration(flow).register().start());
//        };
//    }


    public static String text() {
        return Math.random() > .5 ? "hello World!! @ " + Instant.now() : "holla todo el mundo @ " + Instant.now();
    }



    @Bean
    public IntegrationFlow buildFlow() {
        return IntegrationFlow
//            .from(myMessageSource, p -> p.poller(spec -> spec.fixedDelay(seconds, TimeUnit.SECONDS)))
            .from(greetingsRequests())
            .filter(String.class, source -> source.contains("holla"))
            .transform(
                (GenericTransformer<String, String>) String::toUpperCase
            )
            .channel(greetingsResults())
//            .handle((GenericHandler<String>) (payload, headers) -> {
//                System.out.printf("Here's the payload: %s%n", payload);
//                return null;
//            })
            .get();
    }


}



@Component
class Runner implements ApplicationRunner {

    private final GreetingsClient greetingsClient;

    public Runner(GreetingsClient greetingsClient) {
        this.greetingsClient = greetingsClient;
    }

    public void run(ApplicationArguments args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(this.greetingsClient.greet(IntegrationApplication.text()));
        }
    }

}


@MessagingGateway
interface GreetingsClient {

    @Gateway(requestChannel = "greetingsRequests", replyChannel = "greetingsResults")
    String greet(String text);

}

