package com.julianduru.learning.integration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by Julian Duru on 07/05/2023
 */
@Configuration
public class OrderIntegrationFlow {


    private final Map<Integer, Order> orderDb = new ConcurrentHashMap<>();


    @Bean
    ApplicationRunner runner(MessageChannel orders) {
        return event -> {
            var order = new Order(1, Set.of(new LineItem("sku1"), new LineItem("sku2")));
            orderDb.put(order.id(), order);

            var message = MessageBuilder
                .withPayload(order)
                .setHeader("orderId", order.id())
                .build();

            orders.send(message);
        };
    }


    @Bean
    IntegrationFlow retailerFlow() {
        return IntegrationFlow
            .from(
                orders()
            )
            .split(new AbstractMessageSplitter() {
                @Override
                protected Object splitMessage(Message<?> message) {
                    var order = (Order) message.getPayload();
                    System.out.println("------------------");
                    System.out.println("Got the order: " + order.id());
                    return order.lineItems();
                }
            })
            .handle((GenericHandler<LineItem>) (payload, headers) -> {
                System.out.println("------------------");
                System.out.println("Processing line item: " + payload.sku());
                headers.forEach((k, v) -> System.out.println(k + ": " + v));
                return null;
            })
            .aggregate()
            .handle(new GenericHandler<Object>() {
                @Override
                public Object handle(Object payload, MessageHeaders headers) {
                    System.out.println("------------------");

                    return null;
                }
            })
            .get();
    }


    @Bean
    MessageChannel orders() {
        return MessageChannels.direct().get();
    }


    record Order(Integer id, Set<LineItem> lineItems) {}


    record LineItem(String sku) {}


}


