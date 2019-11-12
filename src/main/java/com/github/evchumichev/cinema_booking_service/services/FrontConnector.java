package com.github.evchumichev.cinema_booking_service.services;

import static spark.Spark.*;

public class FrontConnector {
    private Controller controller;

    public FrontConnector() {
        controller = new Controller();
    }

    public void startApi() {
        get("/api/cinema", (request, response) -> {
            return controller.cinemaList();
        });

        get("/api/show", (request, response) -> {
            return controller.showList(request.queryParams("cinemaID"));
        });

        get("/api/seats", (request, response) -> {
            return controller.seatsList(request.queryParams("showID"));
        });

        post("/api/booking", (request, response) -> {
            String showID = request.queryParams("showID");
            String seatID = request.queryParams("seatID");
            return controller.bookTheSeats(showID, seatID);
        });
    }
}
