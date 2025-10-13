package backend;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private DatabaseManager db;

    public BookingManager(DatabaseManager db){
        this.db = db;
    }

    // Helper method: Check if car is available for the given period
    private boolean isCarAvailable(int carId, Date pickupDate, Date dropoffDate) {
        String sql = "SELECT COUNT(*) AS count FROM Bookings " +
                     "WHERE car_id=? AND " +
                     "((pickup_date <= ? AND dropoff_date >= ?) OR " +
                     "(pickup_date <= ? AND dropoff_date >= ?) OR " +
                     "(pickup_date >= ? AND dropoff_date <= ?))";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, carId);
            stmt.setDate(2, dropoffDate);
            stmt.setDate(3, dropoffDate);
            stmt.setDate(4, pickupDate);
            stmt.setDate(5, pickupDate);
            stmt.setDate(6, pickupDate);
            stmt.setDate(7, dropoffDate);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt("count") == 0;  // true if no overlapping bookings
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false; // default to not available on error
    }

    // Create a new booking
    public boolean addBooking(int userId, int carId, Date pickupDate, Time pickupTime, Date dropoffDate, Time dropoffTime){
        if(!isCarAvailable(carId, pickupDate, dropoffDate)){
            System.out.println("Cannot add booking: Car is already booked for these dates.");
            return false;
        }

        String sql = "INSERT INTO Bookings(user_id, car_id, pickup_date, pickup_time, dropoff_date, dropoff_time) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, carId);
            stmt.setDate(3, pickupDate);
            stmt.setTime(4, pickupTime);
            stmt.setDate(5, dropoffDate);
            stmt.setTime(6, dropoffTime);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Get all bookings of a user
    public List<Booking> getUserBookings(int userId){
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE user_id=?";
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)){
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                bookings.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("car_id"),
                        rs.getDate("pickup_date"),
                        rs.getTime("pickup_time"),
                        rs.getDate("dropoff_date"),
                        rs.getTime("dropoff_time")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookings;
    }

    // Mark a booking as returned (updates car status)
    public boolean markReturned(int bookingId){
        String getCar = "SELECT car_id FROM Bookings WHERE booking_id=?";
        String updateCar = "UPDATE Cars SET Status='Available' WHERE CarID=?";
        try (PreparedStatement stmt1 = db.getConnection().prepareStatement(getCar);
             PreparedStatement stmt2 = db.getConnection().prepareStatement(updateCar)){
            stmt1.setInt(1, bookingId);
            ResultSet rs = stmt1.executeQuery();
            if(rs.next()){
                int carId = rs.getInt("car_id");
                stmt2.setInt(1, carId);
                stmt2.executeUpdate();
                return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
