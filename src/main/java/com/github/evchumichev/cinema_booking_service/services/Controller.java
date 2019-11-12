package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.domains.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Controller {
    private static final String URL = "jdbc:postgresql://localhost:5432/cinema_booking_service";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private JSONSerializer jsonSerializer;

    Controller() {
        DataBaseMigrator.migrate(URL, USER, PASSWORD);
        jsonSerializer = new JSONSerializer();
    }

    public String cinemaList() {
        final String query = "select * " +
                "from cinema";
        String response = null;
        List<Object> cinemaList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareCall(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cinemaList.add(new Cinema(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("city")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response = jsonSerializer.toJSON(cinemaList);
        return response;
    }

    public String showList(String cinemaID) {
        final String query = "select sh.id, m.title, sh.start_time " +
                "from show sh " +
                "join cinema_hall ch on ch.id = sh.cinema_hall_id " +
                "join movie m on m.id = sh.movie_id " +
                "where ch.cinema_id = ?";
        String response = null;
        List<Object> showList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareCall(query);
            statement.setInt(1, Integer.parseInt(cinemaID));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                showList.add(new Show(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getTimestamp("start_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response = jsonSerializer.toJSON(showList);
        return response;
    }

    public String seatsList(String showID) {
        String query = new String("select s.id, s.row, s.number, t.seat_id " +
                "from seat s " +
                "left join tickets t on t.seat_id = s.id " +
                "join show sh on sh.cinema_hall_id = s.cinema_hall_id " +
                "where sh.cinema_hall_id = ? " +
                "order by s.row, s.number");
        String response = null;
        List<Object> seatsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareCall(query);
            statement.setInt(1, Integer.parseInt(showID));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Seat seat = new Seat(resultSet.getInt("id"), resultSet.getString("row"), resultSet.getString("number"));
                if (resultSet.getInt("seat_id") != seat.getId()) {
                    seat.setBookingStatus(BookingStatus.FREE);
                }
                if (resultSet.getInt("seat_id") == seat.getId()) {
                    seat.setBookingStatus(BookingStatus.RESERVED);
                }
                seatsList.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response = jsonSerializer.toJSON(seatsList);
        return response;
    }

    public String bookTheSeats(String showID, String seatID) {
        String query = "Select * from bookTheSeat(?, variadic ?)";
        String response = null;
        String[] seats = seatID.split(",");
        Object[] seatIDArray = new Object[seats.length];
        for (int i = 0; i < seatIDArray.length; i++) {
            seatIDArray[i] = Integer.parseInt(seats[i]);
        }
        List<Object> tickets = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Array idArray = connection.createArrayOf("integer", seatIDArray);
            PreparedStatement statement = connection.prepareCall(query);
            statement.setInt(1, Integer.parseInt(showID));
            statement.setArray(2, idArray);
            System.out.println("query is: " + statement.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(new Ticket(resultSet.getString("movie_title"), resultSet.getString("cinema_name"),
                        resultSet.getString("cinema_hall_number"), resultSet.getString("seat_row"),
                        resultSet.getString("seat_number"), resultSet.getTimestamp("show_start_time"),
                        resultSet.getTimestamp("show_end_time"), resultSet.getInt("ticket_id"), resultSet.getInt("booking_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response = jsonSerializer.toJSON(tickets);

        return response;
    }
}
