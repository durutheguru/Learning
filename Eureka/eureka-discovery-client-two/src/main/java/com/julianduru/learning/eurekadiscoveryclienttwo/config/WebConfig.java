package com.julianduru.learning.eurekadiscoveryclienttwo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * created by julian on 25/08/2022
 */
@Configuration
public class WebConfig {


    @Bean
    public WebClient webClient(
        DiscoveryClient discoveryClient,
        @Value("${code.config.server-one.service-name}") String serviceName
    ) {
        var instance = discoveryClient.getInstances(serviceName).get(0);

        return WebClient.builder()
            .baseUrl(String.format("http://%s:%d", instance.getHost(), instance.getPort()))
            .build();
    }


//    @Bean
//    public WebClient webClient(
//        @Value("${code.config.server-one.url}") String baseUrl
//    ) {
//        return WebClient.builder()
//            .baseUrl(baseUrl)
//            .build();
//    }


}
