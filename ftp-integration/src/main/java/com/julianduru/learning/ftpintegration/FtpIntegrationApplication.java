package com.julianduru.learning.ftpintegration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.dsl.Files;
import org.springframework.messaging.MessageHeaders;

import java.io.File;

@Slf4j
@SpringBootApplication
public class FtpIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpIntegrationApplication.class, args);
	}


	@Bean
	IntegrationFlow inboundFileFlow(@Value("${HOME}/ftp/inbound") File directory) {
		var files = Files
			.inboundAdapter(
				directory
			)
			.autoCreateDirectory(true)
			.preventDuplicates(true)
			.filterFunction(File::isFile);

		return IntegrationFlow
			.from(files)
			.handle(
				(GenericHandler<File>) (payload, headers) -> {
					log.info("Payload: " + payload.getAbsolutePath());
					headers.forEach((k, v) -> log.info("{K:V}: " + k + ": " + v));
					return null;
				}
			).get();
	}

}
