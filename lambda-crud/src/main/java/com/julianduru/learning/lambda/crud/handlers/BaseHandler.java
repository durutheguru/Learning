package com.julianduru.learning.lambda.crud.handlers;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * created by julian on 03/02/2023
 */
@Slf4j
public abstract class BaseHandler<I, O> implements RequestHandler<I, O> {


    static {
        try {
            Class.forName("com.julianduru.learning.lambda.crud.util.HibernateUtil");
        }
        catch (ClassNotFoundException e) {
            log.error("HibernateUtil not found", e);
            throw new RuntimeException(e);
        }

        log.info("Initialization Complete!!");
    }


}
