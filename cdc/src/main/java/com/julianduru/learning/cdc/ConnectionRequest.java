package com.julianduru.learning.cdc;

/**
 * created by Julian Duru on 25/04/2023
 */
public record ConnectionRequest(
    String connectorNamePrefix,
    String host,
    String port,
    String username,
    String password,
    String...databases
) {
}
