package com.github.evchumichev.cinema_booking_service.services.exceptions;

public class WrongRequestParamsException extends BookingServiceAbstractException{
    public WrongRequestParamsException() {
        super();
    }

    public WrongRequestParamsException(String message) {
        super(message);
    }

    public WrongRequestParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongRequestParamsException(Throwable cause) {
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
