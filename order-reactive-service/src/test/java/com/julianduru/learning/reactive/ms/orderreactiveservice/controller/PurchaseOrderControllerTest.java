package com.julianduru.learning.reactive.ms.orderreactiveservice.controller;

import com.julianduru.learning.reactive.ms.orderreactiveservice.client.ProductClient;
import com.julianduru.learning.reactive.ms.orderreactiveservice.client.UserClient;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.PurchaseOrderRequestDto;
import com.julianduru.learning.reactive.ms.orderreactiveservice.dto.PurchaseOrderResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * created by julian on 25/03/2022
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PurchaseOrderControllerTest {


    @Autowired
    private ProductClient productClient;


    @Autowired
    private UserClient userClient;


    private WebClient orderClient;


    @Value("${order.service.url}")
    private String orderUrl;



    @BeforeAll
    public void beforeAll() {
        orderClient = WebClient.builder()
            .baseUrl(orderUrl)
            .build();
    }


    @Test
    public void testUsersMakingPurchaseOrders() throws Exception {
        var products = productClient.getProducts().take(4);
        var users = userClient.getUsers().take(4);

        Flux
            .zip(
                products, users,

                (product, user) -> {
                    var requestDto = new PurchaseOrderRequestDto();

                    requestDto.setUserId(user.getId());
                    requestDto.setProductId(product.getId());

                    return requestDto;
                }
            )
            .flatMap(
                requestDto -> orderClient
                    .post()
                    .bodyValue(requestDto)
                    .retrieve()
                    .bodyToMono(PurchaseOrderResponseDto.class)
            )
            .doOnNext(System.out::println)
            .subscribe();

        Thread.sleep(2000L);
    }


}
