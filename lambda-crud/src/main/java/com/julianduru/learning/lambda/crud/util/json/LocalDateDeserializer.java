package com.julianduru.learning.lambda.crud.util.json;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.julianduru.learning.lambda.crud.util.TimeUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * created by julian
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {


    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalDate.parse(p.getValueAsString(), TimeUtil.DEFAULT_DATE_FORMATTER);
    }


}

