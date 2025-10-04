package backend;

public class Car {
    private int id;
    private String brand;
    private String model;
    private double dailyRate;
    private String status;

    public Car(int id, String brand, String model, double dailyRate, String status){
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
