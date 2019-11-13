package com.github.evchumichev.cinema_booking_service.services;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class HTTPControllerTest {
    private final int port = 4567;
    private static HTTPController httpController;

    @BeforeAll
    public static void initService() {
        new DataBaseMigrator().migrate();
        httpController = new HTTPController();
        httpController.startApi();
    }

    @AfterAll
    public static void stopService() {
        httpController.stop();
    }

    @Test
    public void shouldEqualsCinemaSchema() {
        given().
                port(port).
                when().
                get("/api/cinema").
                then().
                assertThat().body(matchesJsonSchemaInClasspath("cinema-schema.json"));
    }

    @Test
    public void shouldEqualShowSchema() {
        given().
                port(port).
                param("cinemaID", 1).
                when().
                get("/api/show").
                then().
                assertThat().body(matchesJsonSchemaInClasspath("show-schema.json"));
    }

    @Test
    public void shouldEqualSeatSchema() {
        given().
                port(port).
                param("showID", 1).
                when().
                get("/api/seats").
                then().
                assertThat().body(matchesJsonSchemaInClasspath("seat-schema.json"));
    }
}
