package com.julianduru.learning.lambda.crud.util.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.julianduru.learning.lambda.crud.util.TimeUtil;

import java.io.IOException;
import java.time.LocalDate;

/**
 * created by julian
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {


    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(TimeUtil.DEFAULT_DATE_FORMATTER));
    }


}


