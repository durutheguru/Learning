package com.julianduru.learning.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.transformer.FileToByteArrayTransformer;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;

/**
 * created by Julian Duru on 06/05/2023
 */
@IntegrationComponentScan
@Configuration
public class FileSyncIntegrationConfig {



    @Bean
    MessageChannel fileChannel() {
        return MessageChannels.direct().get();
    }


    @Bean
    IntegrationFlow fileSystemFlow(
        MGateway gateway
    ) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gateway.send("Hello World: " + Instant.now());
            }
        }).start();


        return IntegrationFlow.from(
//                Files.inboundAdapter(
//                    Path.of(
//                        SystemPropertyUtils.resolvePlaceholders("${HOME}/Downloads/code_revisions")
//                    ).toFile()
//                ).autoCreateDirectory(true)
//                ,
                fileChannel()
            )
//            .handle(new GenericHandler<File>() {
//                @Override
//                public Object handle(File payload, MessageHeaders headers) {
//                    System.out.println("File: " + payload.getName());
//                    headers.forEach((k, v) -> System.out.println("Header: " + k + " = " + v));
//                    return payload;
//                }
//            })
            .handle(
                Files.outboundAdapter(
                    Path.of(
                        SystemPropertyUtils.resolvePlaceholders("${HOME}/Downloads/code_revisions_out")
                    ).toFile()
                ).autoCreateDirectory(true)
            )
            .get();
    }


}


@MessagingGateway
interface MGateway {

    @Gateway(requestChannel = "fileChannel")
    void send(String message);

}

