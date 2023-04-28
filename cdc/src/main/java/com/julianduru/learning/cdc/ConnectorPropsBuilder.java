package com.julianduru.learning.cdc;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * created by Julian Duru on 25/04/2023
 */
@Slf4j
public class ConnectorPropsBuilder {


    public static Properties mysqlTemplate(ConnectionRequest request) {
        Properties props = new Properties();

        props.setProperty("name", request.connectorNamePrefix() + UUID.randomUUID().toString());
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("database.hostname", request.host());
        props.setProperty("database.port", request.port());
        props.setProperty("database.user", request.username());
        props.setProperty("database.password", request.password());
        props.setProperty("database.allowPublicKeyRetrieval", "true");
        props.setProperty("database.server.id", String.valueOf(Math.abs(new Random(System.currentTimeMillis()).nextLong()/1000)));
        props.setProperty("database.server.name", props.getProperty("name"));
        props.setProperty("topic.prefix", "dbserver1");
        props.setProperty("database.include.list", String.join(",", request.databases()));
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "/tmp/offsets-%d.dat".formatted(System.currentTimeMillis()));
        props.setProperty("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory");
        props.setProperty("schema.history.internal.file.filename", "/tmp/schemahistory-%d.dat".formatted(System.currentTimeMillis()));

        log.info("Connector Properties: {}", props);

        return props;
    }


}
