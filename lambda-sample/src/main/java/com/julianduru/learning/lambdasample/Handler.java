package com.julianduru.learning.lambdasample;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * created by julian on 30/01/2023
 */
public class Handler implements RequestHandler<Map<String, String>, String> {


    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public String handleRequest(Map<String, String> map, Context context) {
        var logger = context.getLogger();
        var response = new String("200 OK - I'm done executing");
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("EVENT: " + gson.toJson(map));
        logger.log("EVENT TYPE: " + map.getClass().toString());
        return response;
    }


}

