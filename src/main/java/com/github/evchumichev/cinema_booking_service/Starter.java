package com.github.evchumichev.cinema_booking_service;

import com.github.evchumichev.cinema_booking_service.services.DataBaseMigrator;
import com.github.evchumichev.cinema_booking_service.services.HTTPController;

public class Starter {
    private static final String URL = "jdbc:postgresql://localhost:5432/cinema_booking_service";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        new DataBaseMigrator().migrate(URL, USER, PASSWORD);
        new HTTPController().startApi();
    }

}
