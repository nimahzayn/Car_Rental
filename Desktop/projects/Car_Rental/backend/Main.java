package backend;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();
        BookingManager bookingManager = new BookingManager(db);

        boolean running = true;

        while (running) {
            System.out.println("\n=== Smart Car Rental Backend Tester ===");
            System.out.println("1. Add Booking");
            System.out.println("2. View User Bookings");
            System.out.println("3. Mark Booking Returned");
            System.out.println("4. Add Car (manual SQL)");
            System.out.println("5. View All Cars (manual SQL)");
            System.out.println("6. Add User (manual SQL)");
            System.out.println("7. View All Users (manual SQL)");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Car ID: ");
                    int carId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Pickup Date (YYYY-MM-DD): ");
                    Date pickupDate = Date.valueOf(sc.nextLine());
                    System.out.print("Pickup Time (HH:MM:SS): ");
                    Time pickupTime = Time.valueOf(sc.nextLine());
                    System.out.print("Drop-off Date (YYYY-MM-DD): ");
                    Date dropDate = Date.valueOf(sc.nextLine());
                    System.out.print("Drop-off Time (HH:MM:SS): ");
                    Time dropTime = Time.valueOf(sc.nextLine());

                    boolean added = bookingManager.addBooking(userId, carId, pickupDate, pickupTime, dropDate, dropTime);
                    System.out.println("Booking added: " + added);
                }
                case 2 -> {
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    List<Booking> bookings = bookingManager.getUserBookings(userId);
                    if (bookings.isEmpty()) System.out.println("No bookings found.");
                    else bookings.forEach(b -> System.out.println("Booking ID: " + b.getId() + " Car ID: " + b.getCarId()));
                }
                case 3 -> {
                    System.out.print("Enter Booking ID to mark returned: ");
                    int bookingId = sc.nextInt();
                    boolean returned = bookingManager.markReturned(bookingId);
                    System.out.println("Booking returned: " + returned);
                }
                case 4 -> {
                    System.out.println("Add Car manually via SQL INSERT into 'car' table with columns (id, brand, model, dailyRate, status).");
                }
                case 5 -> {
                    System.out.println("View Cars manually via SELECT * FROM car;");
                }
                case 6 -> {
                    System.out.println("Add User manually via SQL INSERT into 'user' table with columns (id, name, email, password).");
                }
                case 7 -> {
                    System.out.println("View Users manually via SELECT * FROM user;");
                }
                case 8 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }

        db.closeConnection();
        sc.close();
    }
}
