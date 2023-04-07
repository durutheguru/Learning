package com.julianduru.learning.lambda.crud.handlers.publisher;

import com.julianduru.learning.lambda.crud.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * created by Julian Duru on 16/03/2023
 */
@Slf4j
public class FindPublishersHandlerTest {


    @Test
    public void findPublishers() {
        var handler = new FindPublishersHandler();
        var publishers = handler.handleRequest(null, null);

        log.info("Publishers: {}", JSONUtil.asJsonString(publishers, "--"));
    }


}
