package com.julianduru.learning.reactive.ms.productreactiveservice.repository;

import com.julianduru.learning.reactive.ms.productreactiveservice.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * created by julian on 19/03/2022
 */
@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {


    @Query(
    """
        {
           price: {$lte: ?1, $gte: ?0}
        }
    """
    )
    Flux<Product> findByPriceRanging(int min, int max);


//    Flux<Product> findByPriceBetween(int min, int max);


    Flux<Product> findByPriceBetween(Range<Integer> range);



}
