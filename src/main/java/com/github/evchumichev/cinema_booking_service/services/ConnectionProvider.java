package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.services.configuration.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private Config config = ConfigLoader.getInstance().load();

    public Connection getConnect() {
        try {
            return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
