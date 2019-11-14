package com.github.evchumichev.cinema_booking_service.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigLoader {

    private static ConfigLoader propertiesLoader;
    private String filePath;
    private ConfigLoader() {
        filePath = "database.properties";
    }

    public static ConfigLoader getInstance() {
        if (propertiesLoader == null) {
            propertiesLoader = new ConfigLoader();
        }
        return propertiesLoader;
    }

    public Properties load() {
        Properties properties = new Properties();
        try(FileInputStream stream = new FileInputStream(filePath)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return properties;
    }
}
