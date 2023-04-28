package com.julianduru.learning.cdc;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created by Julian Duru on 25/04/2023
 */
public record Source(

    String version,

    String connector,

    String name,

    @JsonProperty("ts_ms")
    String timestamp,

    String snapshot,

    String db,

    String table,

    @JsonProperty("server_id")
    Long serverId,

    String file,

    String pos

) {
}
