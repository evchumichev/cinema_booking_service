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

    @Test
    public void shouldOKGetCinema() {
        given()
                .port(port)
                .when()
                .get("/api/cinema")
                .then()
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("cinema-schema.json"));
    }

    @Test
    public void shouldOKGetShowCorrectParamValue() {
        given()
                .port(port)
                .param("cinemaID", 1)
                .when()
                .get("/api/show")
                .then()
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("show-schema.json"));
    }

    @Test
    public void shouldExceptionGetShowIncorrectParamValue() {
        given()
                .port(port)
                .param("cinemaID", 23)
                .when()
                .get("/api/show")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldExceptionGetShowEmptyParamCinemaID() {
        given()
                .port(port)
                .when()
                .get("/api/show")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldOKGetSeatsCorrectParamValue() {
        given()
                .port(port)
                .param("showID", 1)
                .when()
                .get("/api/seats")
                .then()
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("seat-schema.json"));
    }

    @Test
    public void shouldExceptionGetSeatsIncorrectParamValue() {
        given()
                .port(port)
                .param("showID", 10)
                .when()
                .get("/api/seats")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldExceptionGetSeatsNoParamShowID() {
        given()
                .port(port)
                .when()
                .get("/api/seats")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @RepeatedTest(2)
    public void shouldOKPostBookingThenExceptionReservedSeat(RepetitionInfo repetitionInfo) {
        if (repetitionInfo.getCurrentRepetition() == 1) {
            given()
                    .port(port)
                    .param("showID", 1)
                    .param("seatID", 1)
                    .when()
                    .post("/api/booking")
                    .then()
                    .statusCode(200)
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
                    .statusCode(400)
                    .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
        }
    }

    @Test
    public void shouldExceptionPostBookingWrongSeatShowCombination() {
        given()
                .port(port)
                .param("showID", 1)
                .param("seatID", 2)
                .when()
                .post("/api/booking")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldOKPostBookingMultipleSeats() {
        given()
                .port(port)
                .param("showID", 3)
                .param("seatID", 2, 4, 6)
                .when()
                .post("/api/booking")
                .then()
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("ticket-schema.json"));
    }

    @Test
    public void shouldExceptionPostBookingNoParams() {
        given()
                .port(port)
                .when()
                .post("/api/booking")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldExceptionPostBookingNoParamShowID() {
        given()
                .port(port)
                .param("seatID", 7)
                .when()
                .post("/api/booking")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    public void shouldExceptionPostBookingNoParamSeatID() {
        given()
                .port(port)
                .param("showID", 1)
                .when()
                .post("/api/booking")
                .then()
                .statusCode(400)
                .assertThat().body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

}
