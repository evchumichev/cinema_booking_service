package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.services.configuration.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static ConfigLoader configLoader;
    private Config config;

    private ConfigLoader() {
        Properties properties = new Properties();
        try (InputStream stream = ConfigLoader.class.getClassLoader().getResourceAsStream("database.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        config = new Config(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"), properties.getProperty("schema"));
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public Config load() {
        return config;
    }
}
