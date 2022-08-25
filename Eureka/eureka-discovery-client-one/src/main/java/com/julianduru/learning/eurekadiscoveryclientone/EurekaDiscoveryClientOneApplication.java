package com.julianduru.learning.eurekadiscoveryclientone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaDiscoveryClientOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryClientOneApplication.class, args);
	}

}
