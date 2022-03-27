package com.julianduru.learning.reactive.ms.orderreactiveservice.service;

import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.PurchaseOrderResponseDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.repository.PurchaseOrderRepository;
import com.julianduru.learning.reactive.ms.orderreactiveservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * created by julian on 23/03/2022
 */
@Service
@RequiredArgsConstructor
public class OrderQueryService {


    private final PurchaseOrderRepository orderRepository;


    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        return Flux.fromStream(() -> orderRepository.findByUserId(userId).stream())
            .map(EntityDtoUtil::getPurchaseOrderResponseDto)
            .subscribeOn(Schedulers.boundedElastic());
    }


}
