-- Step 1: Make a database if it doesn't exist
CREATE DATABASE IF NOT EXISTS veloce_db;

-- Step 2: Use that database
USE veloce_db;

-- Step 3: Make a Users table (like a folder for users)
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

-- Step 4: Make a Cars table (folder for cars)
CREATE TABLE IF NOT EXISTS Cars (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    daily_rate DECIMAL(10,2),
    status ENUM('Available', 'Booked') DEFAULT 'Available'
);

-- Step 5: Make a Bookings table (folder that links users & cars)
CREATE TABLE IF NOT EXISTS Bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    car_id INT,
    pickup_date DATE,
    pickup_time TIME,      -- ⏰ Added pickup time
    dropoff_date DATE,
    dropoff_time TIME,     -- ⏰ Added dropoff time
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (car_id) REFERENCES Cars(car_id)
);

-- Step 6: Insert some sample data (pages inside folders)
INSERT INTO Users (name, email, password) VALUES
('Nimah Zayn', 'nimah@example.com', 'pass123'),
('Meera Rao', 'meera@example.com', 'pass456');

INSERT INTO Cars (brand, model, daily_rate) VALUES
('Toyota', 'Corolla', 2000.00),
('Honda', 'Civic', 2500.00),
('BMW', 'X5', 5000.00);

-- Example booking with pickup/dropoff time
INSERT INTO Bookings (user_id, car_id, pickup_date, pickup_time, dropoff_date, dropoff_time)
VALUES (1, 2, '2025-10-01', '09:30:00', '2025-10-03', '18:00:00');