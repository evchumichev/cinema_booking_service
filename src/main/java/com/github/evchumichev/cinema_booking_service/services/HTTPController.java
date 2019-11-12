package com.github.evchumichev.cinema_booking_service.services;

import java.util.List;

import static spark.Spark.*;

public class HTTPController {
    private CinemaService cinemaService;
    private JSONSerializer jsonSerializer;

    public HTTPController() {
        cinemaService = new CinemaService();
        jsonSerializer = new JSONSerializer();
    }

    public void startApi() {
        get("/api/cinema", (request, response) -> {
            List<Object> responseList = cinemaService.getCinemaList();
            return jsonSerializer.toJSON(responseList);
        });

        get("/api/show", (request, response) -> {
            int cinemaID = Integer.parseInt(request.queryParams("cinemaID"));
            List<Object> responseList = cinemaService.getShowList(cinemaID);
            return jsonSerializer.toJSON(responseList);
        });

        get("/api/seats", (request, response) -> {
            int showID = Integer.parseInt(request.queryParams("showID"));
            List<Object> responseList = cinemaService.getSeatsList(showID);
            return jsonSerializer.toJSON(responseList);
        });

        post("/api/booking", (request, response) -> {
            int showID = Integer.parseInt(request.queryParams("showID"));
            String[] seatIDStringArray = request.queryParamsValues("seatID");
            Integer[] seatID = new Integer[seatIDStringArray.length];
            for (int i = 0; i < seatID.length; i++) {
                seatID[i] = Integer.parseInt(seatIDStringArray[i]);
            }
            List<Object> responseList = cinemaService.bookTheSeats(showID, seatID);
            return jsonSerializer.toJSON(responseList);
        });
    }
}
