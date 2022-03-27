package com.julianduru.learning.reactive.ms.orderreactiveservice.client;

import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.TransactionRequestDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.TransactionResponseDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by julian on 23/03/2022
 */
@Service
public class UserClient {


    private final WebClient webClient;


    public UserClient(@Value("${user.service.url}") String url) {
        this.webClient = WebClient.builder()
            .baseUrl(url)
            .build();
    }


    public Mono<TransactionResponseDto> authorizeTransaction(
        TransactionRequestDto requestDto
    ) {
        return this.webClient.post()
            .uri("transaction")
            .bodyValue(requestDto)
            .retrieve()
            .bodyToMono(TransactionResponseDto.class);
    }


    public Flux<UserDto> getUsers() {
        return this.webClient
            .get()
            .retrieve()
            .bodyToFlux(UserDto.class);
    }


}
