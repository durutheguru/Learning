package com.julianduru.learning.lambdaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class LambdaSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(LambdaSpringApplication.class, args);
	}


	@Bean
	public Function<String, String> sayHello() {
		return (name) -> String.format("Hello %s", name);
	}


}
