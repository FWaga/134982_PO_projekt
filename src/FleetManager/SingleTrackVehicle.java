package FleetManager;

public abstract class SingleTrackVehicle extends Vehicle {
    public SingleTrackVehicle(int id, String brand, String model, int year, String registrationNumber, String type, String status) {
        super(id, brand, model, year, registrationNumber, type, status);
    }
}