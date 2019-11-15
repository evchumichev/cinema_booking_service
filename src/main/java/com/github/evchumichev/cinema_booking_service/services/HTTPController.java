package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.domains.*;
import com.github.evchumichev.cinema_booking_service.domains.Error;

import java.util.List;

import static spark.Spark.*;

public class HTTPController {
    private CinemaService cinemaService;
    private JSONSerializer jsonSerializer;

    public HTTPController() {
        cinemaService = new CinemaService();
        jsonSerializer = new JSONSerializer();
    }

    public void start() {
        get("/api/cinema", (request, response) -> {
            List<Cinema> responseList = cinemaService.getCinemaList();
            return jsonSerializer.toJSON(responseList);
        });

        get("/api/show", (request, response) -> {
            int cinemaID = Integer.parseInt(request.queryParams("cinemaID"));
            List<Show> responseList = cinemaService.getShowList(cinemaID);
            return jsonSerializer.toJSON(responseList);
        });

        get("/api/seats", (request, response) -> {
            int showID = Integer.parseInt(request.queryParams("showID"));
            List<Seat> responseList = cinemaService.getSeatsList(showID);
            return jsonSerializer.toJSON(responseList);
        });


        post("/api/booking", (request, response) -> {
            int showID = Integer.parseInt(request.queryParams("showID"));
            String[] seatIDStringArray = request.queryParams("seatID").split(",");
            Integer[] seatIDIntegerArray = new Integer[seatIDStringArray.length];
            for (int i = 0; i < seatIDIntegerArray.length; i++) {
                seatIDIntegerArray[i] = Integer.parseInt(seatIDStringArray[i]);
            }
            List<Ticket> responseList = cinemaService.bookTheSeats(showID, seatIDIntegerArray);
            return jsonSerializer.toJSON(responseList);
        });

        exception(Exception.class, (exception, request, response) -> {
            String errorMessage = jsonSerializer.toJSON(new Error(exception.getMessage()));
            response.status(400);
            response.type("application/json");
            response.body(errorMessage);
        });
    }

    public void stop() {
        stop();
    }
}
