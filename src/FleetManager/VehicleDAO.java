package FleetManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    private DriverDAO driverDAO;

    public VehicleDAO() {
        this.driverDAO = new DriverDAO();
    }

    public void addVehicle(Vehicle v) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO vehicles (brand, model, year, registration_number, type, status, assigned_driver_id) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, v.getBrand());
            ps.setString(2, v.getModel());
            ps.setInt(3, v.getYear());
            ps.setString(4, v.getRegistrationNumber());
            ps.setString(5, v.getType());
            ps.setString(6, v.getStatus());

            if (v.getAssignedDriver() != null) {
                ps.setInt(7, v.getAssignedDriver().getId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vehicles")) {
            while (rs.next()) {
                String type = rs.getString("type");
                Vehicle v;
                if ("Car".equals(type)) {
                    v = new Car(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getString("registration_number"),
                            rs.getString("status")
                    );
                } else if ("Motorcycle".equals(type)) {
                    v = new Motorcycle(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getString("registration_number"),
                            rs.getString("status")
                    );
                } else if ("Truck".equals(type)) {
                    v = new Truck(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getString("registration_number"),
                            rs.getString("status")
                    );
                } else {
                    continue;
                }

                int driverId = rs.getInt("assigned_driver_id");
                if (!rs.wasNull()) {
                    Driver driver = driverDAO.getDriverById(driverId);
                    v.setAssignedDriver(driver);
                }
                vehicles.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public void deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                System.out.println("Pojazd usunięty z bazy.");
            } else {
                System.out.println("Nie znaleziono pojazdu o podanym ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle(Vehicle v) {
        String sql = "UPDATE vehicles SET brand = ?, model = ?, year = ?, registration_number = ?, type = ?, status = ?, assigned_driver_id = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getBrand());
            ps.setString(2, v.getModel());
            ps.setInt(3, v.getYear());
            ps.setString(4, v.getRegistrationNumber());
            ps.setString(5, v.getType());
            ps.setString(6, v.getStatus());

            if (v.getAssignedDriver() != null) {
                ps.setInt(7, v.getAssignedDriver().getId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ps.setInt(8, v.getId());

            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Pojazd zaktualizowany.");
            } else {
                System.out.println("Nie znaleziono pojazdu o podanym ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vehicle getVehicleById(int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM vehicles WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                Vehicle v;
                if ("Car".equals(type)) {
                    v = new Car(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getString("registration_number"),
                            rs.getString("status")
                    );
                } else if ("Motorcycle".equals(type)) {
                    v = new Motorcycle(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getString("registration_number"),
                            rs.getString("status")
                    );
                } else if ("Truck".equals(type)) {
                    v = new Truck(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getString("registration_number"),
                            rs.getString("status")
                    );
                } else {
                    return null;
                }

                int driverId = rs.getInt("assigned_driver_id");
                if (!rs.wasNull()) {
                    Driver driver = driverDAO.getDriverById(driverId);
                    v.setAssignedDriver(driver);
                }
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateVehicleStatus(int id, String newStatus) {
        String sql = "UPDATE vehicles SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Status pojazdu zaktualizowany.");
            } else {
                System.out.println("Nie znaleziono pojazdu o podanym ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
