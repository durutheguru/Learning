package com.julianduru.learning.kafka.libraryproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianduru.learning.kafka.libraryproducer.domain.Book;
import com.julianduru.learning.kafka.libraryproducer.domain.LibraryEvent;
import com.julianduru.learning.kafka.libraryproducer.producer.LibraryEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by julian on 07/11/2022
 */
@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventsControllerUnitTest {
    
    
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    LibraryEventProducer libraryEventProducer;

    @Test
    void postLibraryEvent() throws Exception {
        //pre-condition
        var library = LibraryEvent.builder()
            .libraryEventId(null)
            .book(
                Book.builder()
                    .bookId(123)
                    .bookAuthor("Henry George")
                    .bookName("Rage on the Night")
                    .build()
            )
            .build();

        doNothing().when(libraryEventProducer).sendLibraryEventRecord(isA(LibraryEvent.class));

        //action
        mockMvc.perform(
            post("/v1/libraryevent")
                .content(objectMapper.writeValueAsString(library))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
            .andExpect(status().isCreated());

        //validation
    }
}
