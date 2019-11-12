package com.github.evchumichev.cinema_booking_service.domains;

public class Seat {
    private int id;
    private String row;
    private String number;
    private BookingStatus bookingStatus;


    public Seat(int id, String row, String number) {
        this.id = id;
        this.row = row;
        this.number = number;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public String getNumber() {
        return number;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
}
