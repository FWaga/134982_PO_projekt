package FleetManager;

public class Car extends Vehicle {
    public Car(int id, String brand, String model, int year, String registrationNumber, String status) {
        super(id, brand, model, year, registrationNumber, status, "Car");
    }
    @Override
    public String getType() {
        return "Car";
    }
}
