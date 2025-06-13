package FleetManager;

public class Truck extends Vehicle {
    public Truck(int id, String brand, String model, int year, String registrationNumber, String status) {
        super(id, brand, model, year, registrationNumber, status, "Car");
    }
    @Override
    public String getType() {
        return "Truck";
    }
}
