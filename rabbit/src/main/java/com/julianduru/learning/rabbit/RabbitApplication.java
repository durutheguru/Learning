package com.julianduru.learning.rabbit;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class RabbitApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitApplication.class, args);
		Thread.currentThread().join();
	}

}


@Configuration
class RabbitConfiguration {


	Exchange exchange() {
		return ExchangeBuilder.directExchange()
	}


}
