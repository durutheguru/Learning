package com.julianduru.learning.reactive.ms.orderreactiveservice.service;

import com.julianduru.learning.reactive.ms.orderreactiveservice.client.ProductClient;
import com.julianduru.learning.reactive.ms.orderreactiveservice.client.UserClient;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.PurchaseOrderRequestDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.PurchaseOrderResponseDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.RequestContext;
import com.julianduru.learning.reactive.ms.orderreactiveservice.repository.PurchaseOrderRepository;
import com.julianduru.learning.reactive.ms.orderreactiveservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * created by julian on 23/03/2022
 */
@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {


    private final ProductClient productClient;


    private final UserClient userClient;


    private final PurchaseOrderRepository purchaseOrderRepository;


    public Mono<PurchaseOrderResponseDto> processOrder(
        Mono<PurchaseOrderRequestDto> requestDtoMono
    ) {
        return requestDtoMono
            .map(RequestContext::new)
            .flatMap(this::productRequestResponse)
            .doOnNext(EntityDtoUtil::setTransactionRequestDto)
            .flatMap(this::userRequestResponse)
            .map(EntityDtoUtil::getPurchaseOrder)
            .publishOn(Schedulers.boundedElastic())
            .map(purchaseOrderRepository::save) //blocking call....
            .map(EntityDtoUtil::getPurchaseOrderResponseDto);
    }


    private Mono<RequestContext> productRequestResponse(RequestContext context) {
        return this.productClient.getProductById(
                context.getPurchaseOrderRequestDto().getProductId()
            )
            .doOnNext(context::setProductDto)
            .retryWhen(Retry.backoff(15, Duration.ofSeconds(1)))
            .thenReturn(context);
    }


    private Mono<RequestContext> userRequestResponse(RequestContext context) {
        return this.userClient.authorizeTransaction(context.getTransactionRequestDto())
            .doOnNext(context::setTransactionResponseDto)
            .thenReturn(context);
    }


}
