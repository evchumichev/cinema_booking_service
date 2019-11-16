package com.github.evchumichev.cinema_booking_service.services.exceptions;

public class BookingServiceAbstractException extends RuntimeException {
    public BookingServiceAbstractException() {
        super();
    }

    public BookingServiceAbstractException(String message) {
        super(message);
    }

    public BookingServiceAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingServiceAbstractException(Throwable cause) {
        super(cause);
    }

    @Override
    public int hashCode() {
        return this.getClass().getSimpleName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BookingServiceAbstractException)) {
            return false;
        }
        return this.getClass().getName().equals(obj.getClass().getName());
    }
}
