package FleetManager;

import java.time.LocalDate;

public class ServiceRecord {
    private int id;
    private Vehicle vehicle;
    private LocalDate serviceDate;
    private String description;

    public ServiceRecord(int id, Vehicle vehicle, LocalDate serviceDate, String description) {
        this.id = id;
        this.vehicle = vehicle;
        this.serviceDate = serviceDate;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public LocalDate getServiceDate() {
        return serviceDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Serwis pojazdu " + vehicle.getBrand() + " " + vehicle.getModel() +
                " (" + vehicle.getRegistrationNumber() + ") w dniu " +
                serviceDate + " - " + description;
    }
}
