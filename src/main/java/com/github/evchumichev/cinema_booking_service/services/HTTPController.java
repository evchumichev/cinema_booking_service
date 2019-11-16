package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.domains.*;
import com.github.evchumichev.cinema_booking_service.domains.Error;
import com.github.evchumichev.cinema_booking_service.services.exceptions.WrongRequestParamsException;

import java.util.List;

import static spark.Spark.*;

public class HTTPController {
    private CinemaDAO cinemaDAO;
    private JSONSerializer jsonSerializer;
    private ResponseStatusStorage responseStatusStorage;

    public HTTPController() {
        cinemaDAO = new CinemaDAO();
        jsonSerializer = new JSONSerializer();
        responseStatusStorage = new ResponseStatusStorage();
    }

    public void start() {
        get("/api/cinema", (request, response) -> {
            List<Cinema> responseList = cinemaDAO.getCinemaList();
            return jsonSerializer.toJSON(responseList);
        });

        get("/api/show", (request, response) -> {
            int cinemaID = 0;
            try {
                cinemaID = Integer.parseInt(request.queryParams("cinemaID"));
            } catch (NumberFormatException e) {
                throw new WrongRequestParamsException("Invalid request parameters!" + e.getMessage(), e);
            }
            List<Show> responseList = cinemaDAO.getShowList(cinemaID);
            if (responseList.isEmpty()) {
                throw new WrongRequestParamsException("Invalid request parameters!");
            }
            return jsonSerializer.toJSON(responseList);
        });

        get("/api/seats", (request, response) -> {
            int showID = 0;
            try {
                showID = Integer.parseInt(request.queryParams("showID"));
            } catch (NumberFormatException e) {
                throw new WrongRequestParamsException("Invalid request parameters!" + e.getMessage(), e);
            }
            List<Seat> responseList = cinemaDAO.getSeatsList(showID);
            if (responseList.isEmpty()) {
                throw new WrongRequestParamsException("Invalid request parameters!");
            }
            return jsonSerializer.toJSON(responseList);
        });


        post("/api/booking", (request, response) -> {
            int showID = 0;
            Integer[] seatIDIntegerArray = null;
            try {
                showID = Integer.parseInt(request.queryParams("showID"));
                String[] seatIDStringArray = request.queryParams("seatID").split(",");
                seatIDIntegerArray = new Integer[seatIDStringArray.length];
                for (int i = 0; i < seatIDIntegerArray.length; i++) {
                    seatIDIntegerArray[i] = Integer.parseInt(seatIDStringArray[i]);
                }
            } catch (NumberFormatException | NullPointerException e) {
                throw new WrongRequestParamsException("Invalid request parameters!" + e.getMessage(), e);
            }
            List<Ticket> responseList = cinemaDAO.bookTheSeats(showID, seatIDIntegerArray);
            if (responseList.isEmpty()) {
                throw new WrongRequestParamsException("Invalid request parameters!");
            }
            return jsonSerializer.toJSON(responseList);
        });

        notFound((request, response) -> "{\n" +
                "    \"message\": \"Invalid URL 404!\"\n" +
                "}");

        exception(Exception.class, (exception, request, response) -> {
            response.status(responseStatusStorage.getResponseCode(exception));
            response.type("application/json");
            response.body(jsonSerializer.toJSON(new Error(exception.getMessage())));
        });
    }

}
