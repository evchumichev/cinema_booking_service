package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.services.configuration.Config;
import org.flywaydb.core.Flyway;

public class DataBaseCleaner {
    private Config config = ConfigLoader.getInstance().load();

    public DataBaseCleaner() {
        loadProperties();
    }

    public void clean() {
        Flyway flyway = Flyway.configure().dataSource(config.getUrl(), config.getUser(), config.getPassword()).schemas(config.getSchema()).load();
        flyway.clean();
    }

    private void loadProperties() {
        ConfigLoader configLoader = ConfigLoader.getInstance();
    }
}
