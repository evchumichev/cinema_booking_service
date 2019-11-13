package com.github.evchumichev.cinema_booking_service;

import com.github.evchumichev.cinema_booking_service.services.DataBaseMigrator;
import com.github.evchumichev.cinema_booking_service.services.HTTPController;
import org.flywaydb.core.Flyway;

public class Starter {

    public static void main(String[] args) {
        new DataBaseMigrator().migrate();
        new HTTPController().startApi();
    }

}
