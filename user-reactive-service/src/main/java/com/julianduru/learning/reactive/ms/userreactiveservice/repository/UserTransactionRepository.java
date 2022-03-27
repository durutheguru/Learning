package com.julianduru.learning.reactive.ms.userreactiveservice.repository;

import com.julianduru.learning.reactive.ms.userreactiveservice.entity.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * created by julian on 21/03/2022
 */
@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer> {





}
