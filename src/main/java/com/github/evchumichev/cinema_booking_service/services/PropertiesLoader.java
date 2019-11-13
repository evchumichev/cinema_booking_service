package com.github.evchumichev.cinema_booking_service.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    public Properties loadFromFIle(String file) {
        Properties properties = new Properties();
        try {
            FileInputStream stream = new FileInputStream(file);
            try {
                properties.load(stream);
            } finally {
                stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
