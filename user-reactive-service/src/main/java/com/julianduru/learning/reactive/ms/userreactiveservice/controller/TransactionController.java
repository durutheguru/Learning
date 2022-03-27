package com.julianduru.learning.reactive.ms.userreactiveservice.controller;

import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionRequestDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionResponseDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * created by julian on 22/03/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/transaction")
public class TransactionController {


    private final TransactionService transactionService;


    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(
        @RequestBody Mono<TransactionRequestDto> requestDto
    ) {
        return requestDto.flatMap(transactionService::createTransaction);
    }


}
