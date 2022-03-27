package com.julianduru.learning.reactive.ms.orderreactiveservice.util;

import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.OrderStatus;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.PurchaseOrderResponseDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.RequestContext;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.TransactionRequestDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.user.TransactionStatus;
import com.julianduru.learning.reactive.ms.orderreactiveservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

/**
 * created by julian on 23/03/2022
 */
public class EntityDtoUtil {


    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder order) {
        var dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(order, dto);
        dto.setOrderId(order.getId());

        return dto;
    }


    public static void setTransactionRequestDto(RequestContext requestContext) {
        var dto = new TransactionRequestDto();

        dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(requestContext.getProductDto().getPrice());

        requestContext.setTransactionRequestDto(dto);
    }


    public static PurchaseOrder getPurchaseOrder(RequestContext context) {
        var order = new PurchaseOrder();

        order.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        order.setProductId(context.getPurchaseOrderRequestDto().getProductId());
        order.setAmount(context.getProductDto().getPrice());

        var transactionStatus = context.getTransactionResponseDto().getStatus();

        var orderStatus = TransactionStatus.APPROVED.equals(transactionStatus)
            ? OrderStatus.COMPLETED : OrderStatus.FAILED;

        order.setStatus(orderStatus);

        return order;
    }


}

