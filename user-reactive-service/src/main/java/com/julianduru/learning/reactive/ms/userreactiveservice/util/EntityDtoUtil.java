package com.julianduru.learning.reactive.ms.userreactiveservice.util;

import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionRequestDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionResponseDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.dto.TransactionStatus;
import com.julianduru.learning.reactive.ms.userreactiveservice.dto.UserDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.entity.User;
import com.julianduru.learning.reactive.ms.userreactiveservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * created by julian on 19/03/2022
 */
public class EntityDtoUtil {


    public static UserDto toDto(User user) {
        var dto = new UserDto();

        dto.setId(user.getId());
        dto.setBalance(user.getBalance());
        dto.setName(user.getName());

        return dto;
    }


    public static User toEntity(UserDto dto) {
        var user = new User();
        BeanUtils.copyProperties(dto, user);

        return user;
    }


    public static UserTransaction toEntity(TransactionRequestDto requestDto) {
        var transaction = new UserTransaction();

        transaction.setUserId(requestDto.getUserId());
        transaction.setAmount(requestDto.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        return transaction;
    }


    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus status) {
        var responseDto = new TransactionResponseDto();

        responseDto.setAmount(requestDto.getAmount());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setStatus(status);

        return responseDto;
    }


}

