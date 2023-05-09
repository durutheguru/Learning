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
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
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
		@Value("${HOME}/ftp/local") File localDirectory,
		@Value("${HOME}/ftp/inbound") File inboundDirectory,
		@Value("${HOME}/ftp/outbound") File outboundDirectory
	) {

		var ftpSessionFactory = new DefaultFtpSessionFactory();
		ftpSessionFactory.setHost("localhost");
		ftpSessionFactory.setPort(2221);
		ftpSessionFactory.setUsername("admin");
		ftpSessionFactory.setPassword("password");

//		var filesIn = Files
//			.inboundAdapter(
//				inboundDirectory
//			)
//			.autoCreateDirectory(true)
////			.preventDuplicates(true)
//			.recursive(true)
//			.scanner(new RecursiveDirectoryScanner());

		var filesIn = Ftp
			.inboundAdapter(ftpSessionFactory)
			.autoCreateLocalDirectory(true)
			.localDirectory(localDirectory)
			.deleteRemoteFiles(true);

		var filesOut = Files
			.outboundAdapter(
				outboundDirectory
			)
			.autoCreateDirectory(true)
//			.deleteSourceFiles(true)
			;

		return IntegrationFlow
			.from(
				filesIn,
				p -> p.poller(pm -> pm.fixedDelay(5L, TimeUnit.SECONDS))
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
