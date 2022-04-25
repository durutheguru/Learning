package com.julianduru.learning.webflux.webfluxzero.webtestclient;

import com.julianduru.learning.webflux.webfluxzero.controller.ReactiveMathController;
import com.julianduru.learning.webflux.webfluxzero.dto.MultiplyRequestDto;
import com.julianduru.learning.webflux.webfluxzero.dto.Response;
import com.julianduru.learning.webflux.webfluxzero.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 07/04/2022
 */
@WebFluxTest({
    ReactiveMathController.class
})
public class ControllerTest {


    @Autowired
    private WebTestClient webClient;


    @MockBean
    private ReactiveMathService mathService;


    @Test
    public void fluentSingleResponseAssertionTest() {
        Mockito.when(mathService.findSquare(Mockito.anyInt()))
                .thenReturn(Mono.just(new Response(225)));

        webClient.get()
            .uri("/reactive-math/square/{number}", 15)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(Response.class)
            .value(r -> {
                assertThat(r.getOutput()).isEqualTo(225);
            });
    }


    @Test
    public void fluentListResponseAssertionTest() {
        var flux = Flux.range(1, 3).map(Response::new);

        Mockito.when(mathService.multiplicationTable(Mockito.anyInt()))
            .thenReturn(flux);

        webClient.get()
            .uri("/reactive-math/table/{number}", 15)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Response.class)
            .hasSize(3);
    }


    @Test
    public void streamingResponseAssertionTest() {
        var flux = Flux.range(1, 3)
            .map(Response::new)
            .delayElements(Duration.ofMillis(100));

        Mockito.when(mathService.multiplicationTable(Mockito.anyInt()))
            .thenReturn(flux);

        webClient.get()
            .uri("/reactive-math/table/{number}/stream", 15)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
            .expectBodyList(Response.class)
            .hasSize(3);
    }


    @Test
    public void testQueryParams() {
        var map = Map.of(
            "count", 10,
            "page", 20
        );

        this.webClient.get()
            .uri(b -> b
                .path("/jobs/search")
                .query("count={count}&page={page}")
                .build(map)
            )
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBodyList(Integer.class)
            .hasSize(2)
            .contains(10, 20);
    }


    @Test
    public void testPostingData() {
        Mockito.when(mathService.multiply(Mockito.any()))
            .thenReturn(Mono.just(new Response(1)));

        webClient.post()
            .uri("/reactive-math/multiply")
            .accept(MediaType.APPLICATION_JSON)
            .headers(h -> h.setBasicAuth("username", "password"))
            .bodyValue(new MultiplyRequestDto())
            .exchange()
            .expectStatus().is2xxSuccessful();
    }


}

