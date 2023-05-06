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
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.SystemPropertyUtils;

import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Consumer;

@IntegrationComponentScan
@SpringBootApplication
public class IntegrationApplication {


    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(IntegrationApplication.class, args);
        Thread.currentThread().join();
    }


//    @Component
//    static class MyMessageSource implements MessageSource<String> {
//
//        @Override
//        public Message<String> receive() {
//            return MessageBuilder.withPayload(text()).build();
//        }
//
//    }
//
//
//	@Bean
//	MessageChannel requests() {
//		return MessageChannels.direct().get();
//	}
//
//
//
//    @Bean
//    MessageChannel replies() {
//        return MessageChannels.direct().get();
//    }
//
//
//
//    public static String text() {
//        return Math.random() > .5 ? "hello World!! @ " + Instant.now() : "holla todo el mundo @ " + Instant.now();
//    }
//
//
//
//    @Bean
//    public IntegrationFlow flow() {
//        return IntegrationFlow
//            .from(requests())
//            .transform(
//                (GenericTransformer<String, String>) String::toUpperCase
//            )
//            .handle((GenericHandler<String>) (payload, headers) -> {
//                System.out.println("Observing payload: " + payload);
//                headers.forEach((k, v) -> System.out.println(k + " : " + v));
//                return payload;
//            })
////            .enrichHeaders(headerEnricherSpec -> headerEnricherSpec.replyChannel(replies()))
////            .channel(replies())
//            .get();
//    }
//


//    @Bean
//    IntegrationFlow inboundFileSystemFlow() {
//        var inboundFileAdapter = Files.inboundAdapter(
//            Path.of(SystemPropertyUtils.resolvePlaceholders("${HOME}/Downloads/code_revisions")).toFile()
//        ).autoCreateDirectory(true);
//
//        return IntegrationFlow.from(inboundFileAdapter)
//            .transform(new FileToStringTransformer())
//            .channel(requests())
//            .get();
//    }
//
//
//    @Bean
//    IntegrationFlow outboundFileSystemFlow() {
//        var outboundFileAdapter = Files.outboundAdapter(
//            Path.of(SystemPropertyUtils.resolvePlaceholders("${HOME}/Downloads/code_revisions_out")).toFile()
//        ).autoCreateDirectory(true);
//
//        return IntegrationFlow.from(replies())
//            .handle(outboundFileAdapter)
//            .get();
//    }


}

//
//@Component
//class Runner implements ApplicationRunner {
//
//    private final GreetingsClient greetingsClient;
//
//    public Runner(GreetingsClient greetingsClient) {
//        this.greetingsClient = greetingsClient;
//    }
//
//    public void run(ApplicationArguments args) {
////        for (int i = 0; i < 100; i++) {
//            System.out.println("The reply is " + this.greetingsClient.greet("julian"));
////        }
//    }
//
//}
//
//
//@MessagingGateway
//interface GreetingsClient {
//
//    @Gateway(requestChannel = "requests", replyChannel = "replies")
//    String greet(String text);
//
//}
//
