package com.github.evchumichev.cinema_booking_service.services;

import org.flywaydb.core.Flyway;

import java.util.Properties;

public class DataBaseCleaner {
    private String url;
    private String user;
    private String password;
    private String schema;

    public DataBaseCleaner() {
        loadProperties();
    }

    public void clean() {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).schemas(schema).load();
        flyway.clean();
    }

    private void loadProperties() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
        Properties properties = configLoader.load();
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.schema = properties.getProperty("schema");
    }
}
