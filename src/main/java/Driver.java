public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    // getters
    public String getDriverID() {
        return driverID;
    }

    public String getName() {
        return name;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    // driver conditions
    public boolean isValidDriverID() {
        // driverID must be unique DATABASE
        // driverID must be exactly 10 chars long
        int length = driverID.length();

        if (length != 10) {
            return false;
        }

        // first two characters cant be 0 or 1
        char first = driverID.charAt(0);
        char second = driverID.charAt(1);

        if (first == 0 || first == 1) {
            return false;
        }
        if (second == 0 || second == 1) {
            return false;
        }

        
        // must be at least 2 special characters between characters 3 and 8
        // last two characters must be uppercase letters
        return true;
    }

    public boolean isValidAddress() {
        // address must be in specific format
        return false;
    }

    public boolean isValidBirthdate() {
        // birthdate must follow certain format
        return false;
    }

    public boolean updateLicenseType() {
        // if driver experience > 10 years, dont change type
        return false;
    }
}
