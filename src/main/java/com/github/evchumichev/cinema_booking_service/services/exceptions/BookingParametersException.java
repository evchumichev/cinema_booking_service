package com.github.evchumichev.cinema_booking_service.services.exceptions;

public class BookingParametersException extends BookingServiceAbstractException {
    public BookingParametersException() {
        super();
    }

    public BookingParametersException(String message) {
        super(message);
    }

    public BookingParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingParametersException(Throwable cause) {
        super(cause);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
