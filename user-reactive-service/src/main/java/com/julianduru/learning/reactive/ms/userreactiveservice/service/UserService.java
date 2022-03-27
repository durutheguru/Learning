package com.julianduru.learning.reactive.ms.userreactiveservice.service;

import com.julianduru.learning.reactive.ms.userreactiveservice.dto.UserDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.repository.UserRepository;
import com.julianduru.learning.reactive.ms.userreactiveservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by julian on 21/03/2022
 */
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public Flux<UserDto> all() {
        return userRepository.findAll().map(EntityDtoUtil::toDto);
    }


    public Mono<UserDto> findById(Integer id) {
        return userRepository.findById(id).map(EntityDtoUtil::toDto);
    }


    public Mono<UserDto> saveUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
            .map(EntityDtoUtil::toEntity)
            .flatMap(userRepository::save)
            .map(EntityDtoUtil::toDto);
    }


    public Mono<UserDto> updateUser(Integer id, Mono<UserDto> userDtoMono) {
        return userRepository.findById(id)
            .flatMap(u -> userDtoMono
                .map(EntityDtoUtil::toEntity)
                .doOnNext(user -> user.setId(id))
            )
            .flatMap(userRepository::save)
            .map(EntityDtoUtil::toDto);
    }


    public Mono<Void> deleteUser(Integer id) {
        return userRepository.deleteById(id);
    }


}
