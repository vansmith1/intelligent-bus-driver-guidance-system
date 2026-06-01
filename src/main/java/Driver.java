import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
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

        if (first < '2' || first > '9') {
            return false;
        }

        if (second < '2' || second > '9') {
            return false;
        }

        // must be at least 2 special characters between characters 3 and 8
        int special = 0;

        for (int i = 2; i <= 7; ++i) {
            char character = driverID.charAt(i);
            
            if (!Character.isLetterOrDigit(character)) {
                special++;
            }
        }

        if (special < 2) {
            return false;
        }

        // last two characters must be uppercase letters
        char last = driverID.charAt(driverID.length() - 1);
        char secondLast = driverID.charAt(driverID.length() - 2);

        if (!(Character.isUpperCase(last))) {
            return false;
        }
        if (!(Character.isUpperCase(secondLast))) {
            return false;
        }
        
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
