package FleetManager;

public abstract class Vehicle {
    protected int id;
    protected String brand;
    protected String model;
    protected int year;
    protected String registrationNumber;
    protected String status;
    protected Driver assignedDriver;
    protected String type;

    public Vehicle(int id, String brand, String model, int year, String registrationNumber, String status, String type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.status = status;
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignedDriver(Driver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public void assignDriver(Driver driver) {
        this.assignedDriver = driver;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }
    @Override
    public String toString() {
        return brand + " " + model + " (" + registrationNumber + ") - Status: " + status;
    }
}
