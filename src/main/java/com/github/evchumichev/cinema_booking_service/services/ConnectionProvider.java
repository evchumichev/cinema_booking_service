package com.github.evchumichev.cinema_booking_service.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {
    private String url;
    private String user;
    private String password;

    public ConnectionProvider() {
        loadProperties();
    }

    public Connection getConnect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void loadProperties() {
        Properties properties = PropertiesLoader.getInstance().loadFromFIle("database.properties");
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }

}
