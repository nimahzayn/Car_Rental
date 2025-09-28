# Veloce – Smart Car Rental System 🚗

**Veloce** is a Java-based car rental system that allows users to search, book, and manage cars seamlessly, while providing admins with full control over cars, users, and bookings.  

---

## Features

### User
- Register and login.  
- Search for cars by location and date.  
- View available cars with detailed specifications and pricing.  
- Book cars and track active rentals.  
- Receive notifications when the rental period ends.  

### Admin
- Add, edit, and remove cars.  
- Manage users (block/unblock).  
- Monitor and update bookings (mark as returned).  
- Generate reports: revenue, popular cars, booking trends.  

---

## Technology Stack

- **Frontend:** Java Servlets & JSP, HTML, CSS, JavaScript  
- **Backend:**  MySQL, JDBC  
- **Database:** MySQL  
- **Programming Principles:** Object-Oriented Programming (OOP)  

---

## Core Entities (OOP Design)

- **Person (abstract)** → `User` | `Admin`  
- **Car**  
- **Booking**  
- **DatabaseManager**  

**Relationships:**  
- One user → many bookings  
- One car → many bookings (only one active at a time)  
- Admin → full access to manage all entities  

---

## How to Run

1. Install and set up **XAMPP**.  
2. Create the MySQL database using the provided schema.  
3. Import the project into your Java IDE (VS Code, Eclipse, etc.).  
4. Configure the database connection in `DatabaseManager.java`.  
5. Deploy the project on a local server (e.g., Tomcat).  
6. Open the homepage and start using the system.  

---

## Project Flow

1. **User:** Register → Search → Book → Receive notifications → Return car.  
2. **Admin:** Manage cars/users/bookings → Generate reports → Mark cars as returned.  
3. **System:** Prevents double-booking, tracks rental duration, maintains accurate availability.  

---

## Contributors

| Name        | GitHub Profile |
|-------------|----------------|
| Nimah Zayn  | [Profile](#)   |
| Nimah Zayn  | [Profile](#)   |
| Nimah Zayn  | [Profile](#)   |
| Nimah Zayn  | [Profile](#)   |
| Nimah Zayn  | [Profile](#)   |
| Nimah Zayn  | [Profile](#)   |
| Nimah Zayn  | [Profile](#)   |

---

## License

This project is for academic purposes and submitted as part of a course requirement.  

