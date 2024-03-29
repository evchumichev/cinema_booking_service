package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.services.exceptions.BookingParametersException;
import com.github.evchumichev.cinema_booking_service.services.exceptions.WrongRequestParamsException;
import com.google.common.collect.ImmutableMap;

public class ResponseStatusStorage {
    private ImmutableMap<Throwable, Integer> immutableMap;


    public ResponseStatusStorage() {

        immutableMap = ImmutableMap.<Throwable, Integer>builder()
                .put(new WrongRequestParamsException(), 400)
                .put(new BookingParametersException(), 400)
                .build();
    }

    public int getResponseCode(Throwable throwable) {
        if (!immutableMap.containsKey(throwable)) {
            return 500;
        }
        return immutableMap.get(throwable);
    }

}
