package com.github.evchumichev.cinema_booking_service.services.exceptions;

public class UnexpectedLogicException extends BookingServiceAbstractException {
    public UnexpectedLogicException() {
        super();
    }

    public UnexpectedLogicException(String message) {
        super(message);
    }

    public UnexpectedLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedLogicException(Throwable cause) {
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
