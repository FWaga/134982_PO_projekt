package FleetManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VehicleDialog extends JDialog {
    private JTextField brandField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField registrationNumberField;
    private JTextField statusField;
    private JComboBox<String> vehicleTypeComboBox;
    private JComboBox<Driver> driverComboBox;

    private Vehicle vehicle;
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;

    private Vehicle createdOrUpdatedVehicle = null;

    public VehicleDialog(Frame owner, Vehicle vehicle, VehicleDAO vehicleDAO, DriverDAO driverDAO) {
        super(owner, true);
        this.vehicle = vehicle;
        this.vehicleDAO = vehicleDAO;
        this.driverDAO = driverDAO;

        setTitle(vehicle == null ? "Dodaj pojazd" : "Edytuj pojazd");
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        inputPanel.add(new JLabel("Marka:"));
        brandField = new JTextField();
        inputPanel.add(brandField);

        inputPanel.add(new JLabel("Model:"));
        modelField = new JTextField();
        inputPanel.add(modelField);

        inputPanel.add(new JLabel("Rok produkcji:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        inputPanel.add(new JLabel("Nr rejestracyjny:"));
        registrationNumberField = new JTextField();
        inputPanel.add(registrationNumberField);

        inputPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        inputPanel.add(statusField);

        inputPanel.add(new JLabel("Typ pojazdu:"));
        String[] vehicleTypes = {"Car", "Motorcycle", "Truck"};
        vehicleTypeComboBox = new JComboBox<>(vehicleTypes);
        inputPanel.add(vehicleTypeComboBox);

        inputPanel.add(new JLabel("Przypisany kierowca:"));
        driverComboBox = new JComboBox<>();
        inputPanel.add(driverComboBox);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Anuluj");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        loadDrivers();

        if (vehicle != null) {
            populateFields(vehicle);
        }

        okButton.addActionListener((ActionEvent e) -> {
            if (validateInput()) {
                createdOrUpdatedVehicle = createOrUpdateVehicleFromInput();
                dispose();
            } else {
                JOptionPane.showMessageDialog(VehicleDialog.this,
                        "Proszę uzupełnić poprawnie wszystkie pola.",
                        "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            createdOrUpdatedVehicle = null;
            dispose();
        });
        pack();
        setLocationRelativeTo(owner);
    }

    private void loadDrivers() {
        driverComboBox.removeAllItems();
        List<Driver> drivers = driverDAO.getAllDrivers();
        for (Driver d : drivers) {
            driverComboBox.addItem(d);
        }
        driverComboBox.setSelectedIndex(-1);
    }

    private void populateFields(Vehicle v) {
        brandField.setText(v.getBrand());
        modelField.setText(v.getModel());
        yearField.setText(String.valueOf(v.getYear()));
        registrationNumberField.setText(v.getRegistrationNumber());
        statusField.setText(v.getStatus());
        vehicleTypeComboBox.setSelectedItem(v.getType());

        Driver assignedDriver = v.getAssignedDriver();
        if (assignedDriver != null) {
            for (int i = 0; i < driverComboBox.getItemCount(); i++) {
                Driver d = driverComboBox.getItemAt(i);
                if (d.getId() == assignedDriver.getId()) {
                    driverComboBox.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            driverComboBox.setSelectedIndex(-1);
        }
    }

    private boolean validateInput() {
        if (brandField.getText().trim().isEmpty()) return false;
        if (modelField.getText().trim().isEmpty()) return false;
        if (registrationNumberField.getText().trim().isEmpty()) return false;
        if (statusField.getText().trim().isEmpty()) return false;
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1886 || year > 2100) return false;
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private Vehicle createOrUpdateVehicleFromInput() {
        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        int year = Integer.parseInt(yearField.getText().trim());
        String regNumber = registrationNumberField.getText().trim();
        String status = statusField.getText().trim();
        String selectedType = (String) vehicleTypeComboBox.getSelectedItem();
        Driver selectedDriver = (Driver) driverComboBox.getSelectedItem();

        Vehicle vehicleToReturn;
        if (vehicle == null) {
            switch (selectedType) {
                case "Car":
                    vehicleToReturn = new Car(0, brand, model, year, regNumber, status);
                    break;
                case "Motorcycle":
                    vehicleToReturn = new Motorcycle(0, brand, model, year, regNumber, status);
                    break;
                case "Truck":
                    vehicleToReturn = new Truck(0, brand, model, year, regNumber, status);
                    break;
                default:
                    throw new IllegalArgumentException("Nieobsługiwany typ pojazdu: " + selectedType);
            }
        } else {
            vehicleToReturn = vehicle;
            vehicleToReturn.setBrand(brand);
            vehicleToReturn.setModel(model);
            vehicleToReturn.setYear(year);
            vehicleToReturn.setRegistrationNumber(regNumber);
            vehicleToReturn.setStatus(status);
            vehicleToReturn.setType(selectedType);
        }
        vehicleToReturn.setAssignedDriver(selectedDriver);
        return vehicleToReturn;
    }
    public Vehicle getCreatedOrUpdatedVehicle() {
        return createdOrUpdatedVehicle;
    }
}
