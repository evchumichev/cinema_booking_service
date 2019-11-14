package com.github.evchumichev.cinema_booking_service.services;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import org.junit.jupiter.api.*;


class HTTPControllerTest {
    private final int port = 4567;
    private static HTTPController httpController;

    @BeforeAll
    public static void initService() {
        new DataBaseCleaner().clean();
        new DataBaseMigrator().migrate();
        httpController = new HTTPController();
        httpController.start();
    }

    @AfterAll
    public static void stopService() {
        httpController.stop();
    }

    @Test
    public void shouldEqualCinemaSchema() {
        given()
                .port(port)
                .when()
                .get("/api/cinema")
                .then()
                .assertThat().body(matchesJsonSchemaInClasspath("cinema-schema.json"));
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
        given()
                .port(port)
                .param("showID", 1)
                .when()
                .get("/api/seats")
                .then()
                .assertThat().body(matchesJsonSchemaInClasspath("seat-schema.json"));
    }

    @RepeatedTest(2)
    public void shouldEqualTicketAndThenErrorSchema(RepetitionInfo repetitionInfo) {
        if (repetitionInfo.getCurrentRepetition() == 1) {
            given()
                    .port(port)
                    .param("showID", 1)
                    .param("seatID", 1)
                    .when()
                    .post("/api/booking")
                    .then()
                    .assertThat().body(matchesJsonSchemaInClasspath("ticket-schema.json"));
        }
        if (repetitionInfo.getCurrentRepetition() == 2) {
            given()
                    .port(port)
                    .param("showID", 1)
                    .param("seatID", 1)
                    .when()
                    .post("/api/booking")
                    .then()
                    .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
        }
    }

    @Test
    public void shouldBeErrorWrongSeat() {
        given()
                .port(port)
                .param("showID", 1)
                .param("seatID", 2)
                .when()
                .post("/api/booking")
                .then().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldBookingMultipleSeats() {
        given()
                .port(port)
                .param("showID", 1)
                .param("seatID", 3, 5)
                .when()
                .post("/api/booking")
                .then()
                .assertThat().body(matchesJsonSchemaInClasspath("ticket-schema.json"));
    }

}
