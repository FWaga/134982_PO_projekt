package FleetManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FleetManagerGUI extends JFrame {
    private VehicleDAO vehicleDAO;
    private DriverDAO driverDAO;
    private ServiceDAO serviceDAO;
    private JTabbedPane tabbedPane;
    private JTable vehicleTable;
    private DefaultTableModel vehicleTableModel;
    private JTable driverTable;
    private DefaultTableModel driverTableModel;
    private JTable serviceTable;
    private DefaultTableModel serviceTableModel;

    public FleetManagerGUI() {
        vehicleDAO = new VehicleDAO();
        driverDAO = new DriverDAO();
        serviceDAO = new ServiceDAO();

        setTitle("System zarządzania flotą pojazdów");
        setSize(1200, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Pojazdy", createVehiclePanel());
        tabbedPane.addTab("Kierowcy", createDriverPanel());
        tabbedPane.addTab("Serwisy", createServicePanel());
        add(tabbedPane);

        loadVehicles();
        loadDrivers();
        loadServices();
    }
    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        vehicleTableModel = new DefaultTableModel(
                new Object[]{"ID", "Marka", "Model", "Rok produkcji", "Numer rejestracyjny", "Typ", "Status", "Kierowca"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 3) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        vehicleTable = new JTable(vehicleTableModel);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(vehicleTableModel);
        vehicleTable.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        panel.add(new JScrollPane(vehicleTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton editButton = new JButton("Edytuj");
        JButton deleteButton = new JButton("Usuń");
        JButton refreshButton = new JButton("Odśwież");

        addButton.addActionListener(e -> addVehicle());
        editButton.addActionListener(e -> editVehicle());
        deleteButton.addActionListener(e -> deleteVehicle());
        refreshButton.addActionListener(e -> loadVehicles());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel createDriverPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        driverTableModel = new DefaultTableModel(
                new Object[]{"ID", "Imię i nazwisko", "Numer prawa jazdy"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };

        driverTable = new JTable(driverTableModel);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(driverTableModel);
        driverTable.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        panel.add(new JScrollPane(driverTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton editButton = new JButton("Edytuj");
        JButton deleteButton = new JButton("Usuń");
        JButton refreshButton = new JButton("Odśwież");

        addButton.addActionListener(e -> addDriver());
        editButton.addActionListener(e -> editDriver());
        deleteButton.addActionListener(e -> deleteDriver());
        refreshButton.addActionListener(e -> loadDrivers());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel createServicePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        serviceTableModel = new DefaultTableModel(
                new Object[]{"ID", "Pojazd", "Data", "Opis"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                } else if (columnIndex == 2) {
                    return LocalDate.class;
                }
                return String.class;
            }
        };

        serviceTable = new JTable(serviceTableModel);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(serviceTableModel);
        serviceTable.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

        panel.add(new JScrollPane(serviceTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Dodaj");
        JButton editButton = new JButton("Edytuj");
        JButton deleteButton = new JButton("Usuń");
        JButton refreshButton = new JButton("Odśwież");

        addButton.addActionListener(e -> addService());
        editButton.addActionListener(e -> editService());
        deleteButton.addActionListener(e -> deleteService());
        refreshButton.addActionListener(e -> loadServices());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadVehicles() {
        vehicleTableModel.setRowCount(0);
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        for (Vehicle v : vehicles) {
            String driverName = (v.getAssignedDriver() != null)
                    ? v.getAssignedDriver().getName() + " " + v.getAssignedDriver().getSurname()
                    : "";
            vehicleTableModel.addRow(new Object[]{
                    v.getId(), v.getBrand(), v.getModel(), v.getYear(),
                    v.getRegistrationNumber(), v.getType(), v.getStatus(), driverName
            });
        }
    }

    private void loadDrivers() {
        driverTableModel.setRowCount(0);
        List<Driver> drivers = driverDAO.getAllDrivers();
        for (Driver d : drivers) {
            String fullName = d.getName() + " " + d.getSurname();
            driverTableModel.addRow(new Object[]{
                    d.getId(), fullName, d.getLicenseNumber()
            });
        }
    }

    private void loadServices() {
        serviceTableModel.setRowCount(0);
        List<ServiceRecord> services = serviceDAO.getAllServiceRecords();
        for (ServiceRecord s : services) {
            String vehicleName = s.getVehicle().getBrand() + " " + s.getVehicle().getModel();
            serviceTableModel.addRow(new Object[]{
                    s.getId(), vehicleName, s.getServiceDate(), s.getDescription()
            });
        }
    }
    private void addVehicle() {
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField regNumberField = new JTextField();
        String[] types = {"Car", "Truck", "Motorcycle"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        String[] statusOptions = {"Aktywny", "W serwisie"};
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Marka:"));
        panel.add(brandField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Rok produkcji:"));
        panel.add(yearField);
        panel.add(new JLabel("Numer rejestracyjny:"));
        panel.add(regNumberField);
        panel.add(new JLabel("Typ:"));
        panel.add(typeCombo);
        panel.add(new JLabel("Status:"));
        panel.add(statusCombo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Dodaj pojazd",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String brand = brandField.getText().trim();
                String model = modelField.getText().trim();
                String yearText = yearField.getText().trim();
                String regNum = regNumberField.getText().trim();
                String status = (String) statusCombo.getSelectedItem();

                if (brand.isEmpty() || model.isEmpty() || yearText.isEmpty() || regNum.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Wszystkie pola muszą być wypełnione.");
                    return;
                }

                int year = Integer.parseInt(yearText);

                String type = (String) typeCombo.getSelectedItem();

                Vehicle vehicle;
                switch (type) {
                    case "Car":
                        vehicle = new Car(0, brand, model, year, regNum, status);
                        break;
                    case "Truck":
                        vehicle = new Truck(0, brand, model, year, regNum, status);
                        break;
                    case "Motorcycle":
                        vehicle = new Motorcycle(0, brand, model, year, regNum, status);
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznany typ pojazdu");
                }
                vehicleDAO.addVehicle(vehicle);
                loadVehicles();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rok musi być liczbą całkowitą.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd danych: " + ex.getMessage());
            }
        }
    }
    private void editVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = vehicleTable.convertRowIndexToModel(selectedRow);
            int id = (int) vehicleTableModel.getValueAt(modelRow, 0);
            Vehicle vehicle = vehicleDAO.getVehicleById(id);

            JTextField regNumberField = new JTextField(vehicle.getRegistrationNumber());

            String[] statusOptions = {"Aktywny", "W serwisie"};
            JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
            statusCombo.setSelectedItem(vehicle.getStatus());

            List<Driver> drivers = driverDAO.getAllDrivers();
            String[] driverOptions = new String[drivers.size() + 1];
            driverOptions[0] = "Brak przypisanego kierowcy";
            for (int i = 0; i < drivers.size(); i++) {
                driverOptions[i + 1] = drivers.get(i).getId() + " - " + drivers.get(i).getName() + " " + drivers.get(i).getSurname();
            }

            JComboBox<String> driverCombo = new JComboBox<>(driverOptions);
            if (vehicle.getAssignedDriver() != null) {
                for (int i = 1; i < driverOptions.length; i++) {
                    if (driverOptions[i].startsWith(vehicle.getAssignedDriver().getId() + " -")) {
                        driverCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nowy numer rejestracyjny:"));
            panel.add(regNumberField);
            panel.add(new JLabel("Nowy status:"));
            panel.add(statusCombo);
            panel.add(new JLabel("Nowy kierowca:"));
            panel.add(driverCombo);

            int result = JOptionPane.showConfirmDialog(this, panel, "Edytuj pojazd",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String newRegNumber = regNumberField.getText();
                String newStatus = (String) statusCombo.getSelectedItem();

                Driver selectedDriver = null;
                if (driverCombo.getSelectedIndex() > 0) {
                    int selectedDriverId = Integer.parseInt(driverOptions[driverCombo.getSelectedIndex()].split(" - ")[0]);
                    selectedDriver = driverDAO.getDriverById(selectedDriverId);
                }

                vehicle.setRegistrationNumber(newRegNumber);
                vehicle.setStatus(newStatus);
                vehicle.setAssignedDriver(selectedDriver);

                vehicleDAO.updateVehicle(vehicle);
                loadVehicles();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wybierz pojazd do edycji.");
        }
    }

    private void deleteVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = vehicleTable.convertRowIndexToModel(selectedRow);
            int id = (int) vehicleTableModel.getValueAt(modelRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Czy na pewno chcesz usunąć pojazd ID " + id + "?",
                    "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                vehicleDAO.deleteVehicle(id);
                loadVehicles();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wybierz pojazd do usunięcia.");
        }
    }
    private void addDriver() {
        String name = JOptionPane.showInputDialog("Imię kierowcy:");
        String surname = JOptionPane.showInputDialog("Nazwisko kierowcy:");
        String licenseNumber = JOptionPane.showInputDialog("Numer prawa jazdy:");

        if (name != null && surname != null && licenseNumber != null) {
            Driver newDriver = new Driver(0, name, surname, licenseNumber);
            driverDAO.addDriver(newDriver);
        }
    }
    private void editDriver() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Wybierz kierowcę do edycji.");
            return;
        }

        int driverId = (int) driverTable.getValueAt(selectedRow, 0);
        Driver driver = driverDAO.getDriverById(driverId);

        String newName = JOptionPane.showInputDialog("Nowe imię:", driver.getName());
        String newSurname = JOptionPane.showInputDialog("Nowe nazwisko:", driver.getSurname());
        String newLicenseNumber = JOptionPane.showInputDialog("Nowy numer prawa jazdy:", driver.getLicenseNumber());

        if (newName != null && newSurname != null && newLicenseNumber != null) {
            driver.setName(newName);
            driver.setSurname(newSurname);
            driver.setLicenseNumber(newLicenseNumber);
            driverDAO.updateDriver(driver);
        }
    }
    private void deleteDriver() {
        int selectedRow = driverTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Wybierz kierowcę do usunięcia.");
            return;
        }

        int driverId = (int) driverTable.getValueAt(selectedRow, 0);
        driverDAO.deleteDriver(driverId);
    }
    private Vehicle selectVehicle() {
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
        String[] options = vehicles.stream().map(v -> v.getId() + " - " + v.getBrand() + " " + v.getModel()).toArray(String[]::new);
        String selection = (String) JOptionPane.showInputDialog(null, "Wybierz pojazd:", "Pojazdy",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selection != null) {
            int vehicleId = Integer.parseInt(selection.split(" - ")[0]);
            return vehicleDAO.getVehicleById(vehicleId);
        }
        return null;
    }
    private void addService() {
        Vehicle vehicle = selectVehicle();
        if (vehicle == null) return;

        String dateInput = JOptionPane.showInputDialog("Data serwisu (RRRR-MM-DD):", LocalDate.now());
        if (dateInput == null) return;

        try {
            LocalDate serviceDate = LocalDate.parse(dateInput);
            String description = JOptionPane.showInputDialog("Opis serwisu:");
            if (description != null && !description.trim().isEmpty()) {
                ServiceRecord record = new ServiceRecord(0, vehicle, serviceDate, description);
                serviceDAO.addServiceRecord(record);
                loadServices();
            } else {
                JOptionPane.showMessageDialog(this, "Opis nie może być pusty.");
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Niepoprawny format daty. Użyj RRRR-MM-DD.");
        }
    }
    private void editService() {
        int selectedRow = serviceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Wybierz wpis serwisowy do edycji.");
            return;
        }

        int serviceId = (int) serviceTable.getValueAt(selectedRow, 0);
        ServiceRecord record = serviceDAO.getServiceRecordById(serviceId);

        String newDescription = JOptionPane.showInputDialog("Nowy opis:", record.getDescription());
        if (newDescription != null) {
            record.setDescription(newDescription);
            serviceDAO.updateServiceRecord(record);
        }
    }
    private void deleteService() {
        int selectedRow = serviceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Wybierz wpis serwisowy do usunięcia.");
            return;
        }

        int serviceId = (int) serviceTable.getValueAt(selectedRow, 0);
        serviceDAO.deleteServiceRecord(serviceId);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FleetManagerGUI().setVisible(true));
    }
}
