package com.github.evchumichev.cinema_booking_service.services;

import org.flywaydb.core.Flyway;

public class DataBaseMigrator {

    public void migrate(String url, String user, String password) {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
    }

}
