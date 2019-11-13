package com.github.evchumichev.cinema_booking_service.domains;

import java.sql.Timestamp;

public class Ticket {
    private int ticketID;
    private int bookingID;
    private String movieTitle;
    private String cinemaName;
    private String cinemaHallNumber;
    private String seatRow;
    private String seatNumber;
    private Timestamp showStart;
    private Timestamp showEnd;

    public Ticket(String movieTitle, String cinemaName, String cinemaHallNumber, String seatRow, String seatNumber, Timestamp showStart, Timestamp showEnd, int ticketNumber, int bookingNumber) {
        this.movieTitle = movieTitle;
        this.cinemaName = cinemaName;
        this.cinemaHallNumber = cinemaHallNumber;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.showStart = showStart;
        this.showEnd = showEnd;
        this.ticketID = ticketNumber;
        this.bookingID = bookingNumber;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getCinemaHallNumber() {
        return cinemaHallNumber;
    }

    public String getSeatRow() {
        return seatRow;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Timestamp getShowStart() {
        return showStart;
    }

    public Timestamp getShowEnd() {
        return showEnd;
    }

    public int getTicketID() {
        return ticketID;
    }

    public int getBookingID() {
        return bookingID;
    }
}