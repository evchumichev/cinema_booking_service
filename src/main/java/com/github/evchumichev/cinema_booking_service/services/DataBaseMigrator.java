package com.github.evchumichev.cinema_booking_service.services;

import org.flywaydb.core.Flyway;

import java.util.Properties;

public class DataBaseMigrator {
    private String url;
    private String user;
    private String password;
    private String schema;

    public DataBaseMigrator(){
        loadProperties();
    }

    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).schemas(schema).load();
        flyway.migrate();
    }

    private void loadProperties() {
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        Properties properties = propertiesLoader.loadFromFIle("database.properties");
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
        this.schema = properties.getProperty("schema");
    }

}
