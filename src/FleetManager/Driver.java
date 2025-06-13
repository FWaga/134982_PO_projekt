package FleetManager;

public class Driver {
    private int id;
    private String name;
    private String surname;
    private String licenseNumber;

    public Driver(int id, String name, String surname, String licenseNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public String toString() {
        return name + " " + surname + " (licencja: " + licenseNumber + ")";
    }
}
