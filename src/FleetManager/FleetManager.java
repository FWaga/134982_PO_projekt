package FleetManager;

import java.util.ArrayList;
import java.util.List;

public class    FleetManager {
    private List<Vehicle> vehicles = new ArrayList<>();

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void removeVehicle(int id) {
        vehicles.removeIf(v -> v.getId() == id);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }
}
