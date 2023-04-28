package com.julianduru.learning.cdc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * created by Julian Duru on 25/04/2023
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Payload (
    Map<String, Object> before,

    Map<String, Object> after,

    String op,

    @JsonProperty("ts_ms")
    String timestamp,

    Source source
){

    public String sourceId() {
        return source.db() + "." + source.table();
    }

    public boolean isCreate() {
        return "c".equals(op);
    }

    public boolean isUpdate() {
        return "u".equals(op);
    }

    public boolean isDelete() {
        return "d".equals(op);
    }

}
