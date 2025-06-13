package FleetManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    public void addDriver(Driver d) {
        String sql = "INSERT INTO drivers (name, surname, license_number) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getName());
            ps.setString(2, d.getSurname());
            ps.setString(3, d.getLicenseNumber());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("license_number")
                );
                drivers.add(driver);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
    public void updateDriver(Driver d) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE drivers SET name = ?, surname = ?, license_number = ? WHERE id = ?")) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getSurname());
            ps.setString(3, d.getLicenseNumber());
            ps.setInt(4, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDriver(int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM drivers WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Driver getDriverById(int id) {
        String sql = "SELECT * FROM drivers WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("license_number")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
