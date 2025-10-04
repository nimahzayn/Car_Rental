package backend;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();
        BookingManager bookingManager = new BookingManager(db);

        // Example: Add a booking
        boolean added = bookingManager.addBooking(1, 2,
                Date.valueOf("2025-10-01"),
                Time.valueOf("09:30:00"),
                Date.valueOf("2025-10-03"),
                Time.valueOf("18:00:00"));

        System.out.println("Booking added: " + added);

        // Example: Get user bookings
        List<Booking> bookings = bookingManager.getUserBookings(1);
        for (Booking b : bookings){
            System.out.println("Booking ID: " + b.getId() + " Car ID: " + b.getCarId());
        }

        // Example: Mark returned
        boolean returned = bookingManager.markReturned(1);
        System.out.println("Booking returned: " + returned);

        db.closeConnection();
    }
}
