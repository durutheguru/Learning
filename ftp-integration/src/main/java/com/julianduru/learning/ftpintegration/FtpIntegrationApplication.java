package com.julianduru.learning.ftpintegration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.RecursiveDirectoryScanner;
import org.springframework.integration.file.dsl.Files;
import org.springframework.messaging.MessageHeaders;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class FtpIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpIntegrationApplication.class, args);
	}


	@Bean
	IntegrationFlow inboundFileFlow(
		@Value("${HOME}/ftp/inbound") File inboundDirectory,
		@Value("${HOME}/ftp/outbound") File outboundDirectory
	) {
		var filesIn = Files
			.inboundAdapter(
				inboundDirectory
			)
			.autoCreateDirectory(true)
			.recursive(true)
			.scanner(new RecursiveDirectoryScanner());

		var filesOut = Files
			.outboundAdapter(
				outboundDirectory
			)
			.autoCreateDirectory(true);

		return IntegrationFlow
			.from(
				filesIn,
				p -> p.poller(pm -> pm.fixedDelay(10L, TimeUnit.SECONDS))
			)
			.handle(
				(GenericHandler<File>) (payload, headers) -> {
					log.info("Payload: " + payload.getAbsolutePath());
					headers.forEach((k, v) -> log.info("{K:V}: " + k + ": " + v));
					return payload;
				}
			)
			.handle(filesOut)
			.get();
	}

}
