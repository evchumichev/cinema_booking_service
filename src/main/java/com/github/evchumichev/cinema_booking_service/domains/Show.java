package com.github.evchumichev.cinema_booking_service.domains;

import java.sql.Timestamp;

public class Show {
    private int id;
    private String movieTitle;
    private Timestamp showStartTime;

    public Show(int showID, String movieTitle, Timestamp showStartTime) {
        this.id = showID;
        this.movieTitle = movieTitle;
        this.showStartTime = showStartTime;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public Timestamp getShowStartTime() {
        return showStartTime;
    }

    public int getId() {
        return id;
    }
}