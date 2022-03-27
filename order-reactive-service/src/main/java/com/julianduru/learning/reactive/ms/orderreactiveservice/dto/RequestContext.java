package com.julianduru.learning.reactive.ms.orderreactiveservice.dto;

import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.product.ProductDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.TransactionRequestDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.TransactionResponseDto;
import lombok.Data;
import lombok.ToString;

/**
 * created by julian on 23/03/2022
 */
@Data
@ToString
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;


    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }


}


