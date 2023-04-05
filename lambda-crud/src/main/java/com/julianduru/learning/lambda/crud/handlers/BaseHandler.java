package com.julianduru.learning.lambda.crud.handlers;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.julianduru.learning.lambda.crud.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.crac.Core;
import org.crac.Resource;

/**
 * created by julian on 03/02/2023
 */
@Slf4j
public abstract class BaseHandler<I, O> implements RequestHandler<I, O>, Resource {


    protected HibernateUtil hibernateUtil = new HibernateUtil();


    public BaseHandler() {
        log.info("Registering Handler in Core Global Context: {}", getClass().getName());
        Core.getGlobalContext().register(this);
        setup();
    }


    private void setup() {
        hibernateUtil.setup();
        log.info("Initialization Complete!!");
    }


    @Override
    public void beforeCheckpoint(org.crac.Context<? extends Resource> context) throws Exception {
        log.info("Before Checkpoint - {}", getClass().getName());
        hibernateUtil.getSessionFactory().close();
    }


    @Override
    public void afterRestore(org.crac.Context<? extends Resource> context) throws Exception {
        log.info("After Restore Checkpoint - {}", getClass().getName());
    }


}

