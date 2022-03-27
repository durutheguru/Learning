package com.julianduru.learning.reactive.ms.userreactiveservice.service;

import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionRequestDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionResponseDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionStatus;
import com.julianduru.learning.reactive.ms.userreactiveservice.repository.UserRepository;
import com.julianduru.learning.reactive.ms.userreactiveservice.repository.UserTransactionRepository;
import com.julianduru.learning.reactive.ms.userreactiveservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * created by julian on 22/03/2022
 */
@Service
@RequiredArgsConstructor
public class TransactionService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserTransactionRepository transactionRepository;


    public Mono<TransactionResponseDto> createTransaction(
        final TransactionRequestDto requestDto
    ) {
        return userRepository
            .updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
            .filter(Boolean::booleanValue)
            .map(b -> EntityDtoUtil.toEntity(requestDto))
            .flatMap(transactionRepository::save)
            .map(t -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
            .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }


}
