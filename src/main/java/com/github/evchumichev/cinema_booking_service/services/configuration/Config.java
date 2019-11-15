package com.github.evchumichev.cinema_booking_service.services.configuration;

public class Config {
    private String url;
    private String user;
    private String password;
    private String schema;

    public Config(String url, String user, String name, String schema) {
        this.url = url;
        this.user = user;
        this.password = name;
        this.schema = schema;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSchema() {
        return schema;
    }
}
