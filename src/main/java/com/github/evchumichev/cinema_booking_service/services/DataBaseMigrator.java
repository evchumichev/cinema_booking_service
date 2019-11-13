package com.github.evchumichev.cinema_booking_service.services;

import org.flywaydb.core.Flyway;

import java.util.Properties;

public class DataBaseMigrator {
    private String url;
    private String user;
    private String password;

    public DataBaseMigrator(){
        loadProperties();
    }

    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
    }

    private void loadProperties() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties = propertiesLoader.loadFromFIle("database.properties");
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

}
