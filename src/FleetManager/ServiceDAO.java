package FleetManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    public void addServiceRecord(ServiceRecord s) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO services (vehicle_id, service_date, description) VALUES (?, ?, ?)")) {
            ps.setInt(1, s.getVehicle().getId());
            ps.setDate(2, Date.valueOf(s.getServiceDate()));
            ps.setString(3, s.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ServiceRecord> getAllServiceRecords() {
        List<ServiceRecord> records = new ArrayList<>();
        VehicleDAO vehicleDAO = new VehicleDAO();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM services")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int vehicleId = rs.getInt("vehicle_id");
                LocalDate serviceDate = rs.getDate("service_date").toLocalDate();
                String description = rs.getString("description");

                Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

                ServiceRecord record = new ServiceRecord(id, vehicle, serviceDate, description);
                records.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void updateServiceRecord(ServiceRecord s) {
        String sql = "UPDATE services SET vehicle_id = ?, service_date = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.getVehicle().getId());
            ps.setDate(2, Date.valueOf(s.getServiceDate()));
            ps.setString(3, s.getDescription());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteServiceRecord(int id) {
        String sql = "DELETE FROM services WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ServiceRecord getServiceRecordById(int id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        VehicleDAO vehicleDAO = new VehicleDAO();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int vehicleId = rs.getInt("vehicle_id");
                LocalDate serviceDate = rs.getDate("service_date").toLocalDate();
                String description = rs.getString("description");

                Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

                return new ServiceRecord(id, vehicle, serviceDate, description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
