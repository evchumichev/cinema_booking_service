package com.github.evchumichev.cinema_booking_service.services;

import org.flywaydb.core.Flyway;

class DataBaseMigrator {
    static void migrate(String URL, String USER, String PASSWORD) {
        Flyway flyway = Flyway.configure().dataSource(URL, USER, PASSWORD).load();
        flyway.migrate();
    }

    private DataBaseMigrator() {
    }
}
