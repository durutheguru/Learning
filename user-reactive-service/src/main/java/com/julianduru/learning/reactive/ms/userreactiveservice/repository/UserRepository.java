package com.julianduru.learning.reactive.ms.userreactiveservice.repository;

import com.julianduru.learning.reactive.ms.userreactiveservice.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * created by julian on 21/03/2022
 */
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {


    @Modifying
    @Query(
    """
        UPDATE users SET balance = balance - :amount WHERE
        id = :userId AND balance >= :amount
    """
    )
    Mono<Boolean> updateUserBalance(Integer userId, Integer amount);


}


