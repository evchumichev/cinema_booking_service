package com.github.evchumichev.cinema_booking_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JSONSerializer {
    private ObjectMapper objectMapper;

    public JSONSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    public String toJSON(List<Object> objects) {
        String response = null;
        try {
            response = objectMapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }
}