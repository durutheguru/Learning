package com.julianduru.learning.reactive.ms.userreactiveservice.controller;

import com.julianduru.learning.reactive.ms.userreactiveservice.dto.UserDto;
import com.julianduru.learning.reactive.ms.userreactiveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * created by julian on 21/03/2022
 */
@RestController
@RequestMapping("user")
public record UserController(UserService userService) {


    @GetMapping
    public Flux<UserDto> all() {
        return userService.all();
    }


    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable Integer id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Mono<UserDto> saveUser(@RequestBody Mono<UserDto> userDtoMono) {
        return userService.saveUser(userDtoMono);
    }


    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(
        @PathVariable Integer id, @RequestBody Mono<UserDto> userDtoMono
    ) {
        return userService.updateUser(id, userDtoMono)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }


}

