package com.julianduru.learning.cdc;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@EnableAsync
@SpringBootApplication
public class CdcApplication {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	private ExecutorService executor = Executors.newCachedThreadPool();

	public static final MultiValueMap<String, Payload> payloads = new LinkedMultiValueMap<>();


	public static void main(String[] args) {
		SpringApplication.run(CdcApplication.class, args);
	}


	public void setupConnector(ConnectionRequest request, Consumer<Payload> payloadConsumer) {
		executor.execute(() -> {
			final Properties props = ConnectorPropsBuilder.mysqlTemplate(request);

			try (
				DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
					.using(props)
					.notifying(record -> {
						try {
							Payload payload = JSONUtil.readPayload(record.value());
							payloadConsumer.accept(payload);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					})
					.build()
			) {
				engine.run();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});
	}


	//	@Async
	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		setupConnector(
			new ConnectionRequest(
				"docker_connector",
				"localhost",
				"33080",
				"root",
				"1234567890",
				"lambda_crud", "bookshop"
			),

			payload -> {
				if (payload.isCreate()) {
					System.out.println("Created a New object: " + payload);
					payloads.add(payload.sourceId(), payload);
				}
			}
		);
	}


}

