package com.julianduru.learning.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.transformer.FileToByteArrayTransformer;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.SystemPropertyUtils;

import java.nio.file.Path;

/**
 * created by Julian Duru on 06/05/2023
 */
@Configuration
public class FileSyncIntegrationConfig {


    @Bean
    MessageChannel fileChannel() {
        return MessageChannels.direct().get();
    }


    @Bean
    IntegrationFlow fileSystemFlow() {
        return IntegrationFlow.from(
                Files.inboundAdapter(
                    Path.of(
                        SystemPropertyUtils.resolvePlaceholders("${HOME}/Downloads/code_revisions")
                    ).toFile()
                ).autoCreateDirectory(true)
            )
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
