package com.github.evchumichev.cinema_booking_service.services;

import com.github.evchumichev.cinema_booking_service.domains.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CinemaService {
    private static final String URL = "jdbc:postgresql://localhost:5432/cinema_booking_service";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public List<Object> getCinemaList() {
        List<Object> cinemaList = new ArrayList<>();
        final String query = "select *\n" +
                "from cinema";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareCall(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    cinemaList.add(new Cinema(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("city")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaList;
    }

    public List<Object> getShowList(int cinemaID) {
        List<Object> showList = new ArrayList<>();
        final String query = "select sh.id, m.title, sh.start_time\n" +
                "from show sh\n"+
                "join cinema_hall ch on ch.id = sh.cinema_hall_id\n" +
                "join movie m on m.id = sh.movie_id\n" +
                "where ch.cinema_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareCall(query)) {
                statement.setInt(1, cinemaID);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    showList.add(new Show(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getTimestamp("start_time")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showList;
    }

    public List<Object> getSeatsList(int showID) {
        List<Object> seatsList = new ArrayList<>();
        String query = "select s.id, s.row, s.number, t.seat_id\n" +
                "from seat s\n" +
                "left join tickets t on t.seat_id = s.id\n" +
                "join show sh on sh.cinema_hall_id = s.cinema_hall_id\n" +
                "where sh.cinema_hall_id = ?\n" +
                "order by s.row, s.number";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareCall(query)) {
                statement.setInt(1, showID);
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seatsList;
    }

    public List<Object> bookTheSeats(int showID, Integer[] seatID) {
        int bookingID = -1;
        List<Object> tickets = new ArrayList<>();
        String firstQuery = "select * from bookTheSeat(?, variadic ?)";
        String secondQuery = "select m.title as movie_title, c.name as cinema_name, ch.number as cinema_hall_number, s.row as seat_row,\n" +
                " s.number as seat_number, sh.start_time as show_start_time, sh.end_time as show_end_time, t.id as ticket_id, b.id as booking_id \n" +
                "                        from booking b \n" +
                "                        join tickets t on t.booking_id = b.id \n" +
                "                        join show sh on sh.id = t.show_id \n" +
                "                        join movie m on m.id = sh.movie_id \n" +
                "                        join cinema_hall ch on ch.id = sh.cinema_hall_id \n" +
                "                        join cinema c on c.id = ch.cinema_id \n" +
                "                        join seat s on s.id = t.seat_id \n" +
                "                where b.id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareCall(firstQuery)) {
                statement.setInt(1, showID);
                statement.setArray(2, connection.createArrayOf("integer", seatID));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    bookingID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement statement = connection.prepareCall(secondQuery)) {
                statement.setInt(1, bookingID);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    tickets.add(new Ticket(resultSet.getString("movie_title"), resultSet.getString("cinema_name"),
                            resultSet.getString("cinema_hall_number"), resultSet.getString("seat_row"),
                            resultSet.getString("seat_number"), resultSet.getTimestamp("show_start_time"),
                            resultSet.getTimestamp("show_end_time"), resultSet.getInt("ticket_id"), resultSet.getInt("booking_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
