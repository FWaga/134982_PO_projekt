package FleetManager;

public class Motorcycle extends Vehicle {
    public Motorcycle(int id, String brand, String model, int year, String registrationNumber, String status) {
        super(id, brand, model, year, registrationNumber, status, "Car");
    }
    @Override
    public String getType() {
        return "Motorcycle";
    }
}