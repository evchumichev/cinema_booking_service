package com.github.evchumichev.cinema_booking_service;

import com.github.evchumichev.cinema_booking_service.services.FrontConnector;

public class Starter {
    public static void main(String[] args) {
        new FrontConnector().startApi();
    }

}
