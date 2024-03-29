package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.services.configuration.Config;
import org.flywaydb.core.Flyway;

public class DataBaseMigrator {
    private Config config = ConfigLoader.getInstance().load();

    public void migrate() {
        Flyway flyway = Flyway.configure().dataSource(config.getUrl(), config.getUser(), config.getPassword()).schemas(config.getSchema()).load();
        flyway.migrate();
    }


}
