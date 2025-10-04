package backend;

import java.sql.Date;
import java.sql.Time;

public class Booking {
    private int id;
    private int userId;
    private int carId;
    private Date pickupDate;
    private Time pickupTime;
    private Date dropoffDate;
    private Time dropoffTime;

    public Booking(int id, int userId, int carId, Date pickupDate, Time pickupTime, Date dropoffDate, Time dropoffTime){
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.dropoffDate = dropoffDate;
        this.dropoffTime = dropoffTime;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getCarId() { return carId; }
    public Date getPickupDate() { return pickupDate; }
    public Time getPickupTime() { return pickupTime; }
    public Date getDropoffDate() { return dropoffDate; }
    public Time getDropoffTime() { return dropoffTime; }
}
