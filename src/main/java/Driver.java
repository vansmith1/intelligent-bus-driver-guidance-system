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

    // get age
    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    // get driver id
    public String getDriverID() {
        return driverID;
    }
    
    // get name
    public String getName() {
        return name;
    }

    // get years of experience
    public int getExperienceYears() {
        return experienceYears;
    }

    // get type of license
    public String getLicenseType() {
        return licenseType;
    }

    // get address
    public String getAddress() {
        return address;
    }

    // get birth date
    public String getBirthdate() {
        return birthdate;
    }

    // driver conditions
    // checks driver id is valid
    public boolean isValidDriverID() {
        // driverID must be exactly 10 chars long
        if (driverID == null) {
            return false;
        }

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

    // checks address is valid
    public boolean isValidAddress() {
        // address must be in specific format
        int dividers = 0;

        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == '|') {
                dividers++;
            }
        }

        // missing field
        if (dividers != 4) {
            return false;
        }

        // missing field at start or end
        if (address.charAt(0) == '|' || address.charAt(address.length() - 1) == '|') {
            return false;
        }

        // missing middle field
        if (address.contains("||")) {
            return false;
        }

        return true;
    }

    // checks birth date is valid
    public boolean isValidBirthdate() {
        // birthdate must be in specific format
        if (birthdate == null || birthdate.length() != 10) {
            return false;
        }

        int dividers = 0;

        for (int i = 0; i < birthdate.length(); i++) {
            if (birthdate.charAt(i) == '-') {
                dividers++;
            }
        }

        // missing field
        if (dividers != 2) {
            return false;
        }

        // missing field at start or end
        if (birthdate.charAt(0) == '-' || birthdate.charAt(birthdate.length() - 1) == '-') {
            return false;
        }

        // missing middle field
        if (birthdate.contains("--")) {
            return false;
        }

        int day = Integer.parseInt(birthdate.substring(0, 2));
        int month = Integer.parseInt(birthdate.substring(3, 5));

        // checks valid day
        if (day < 1 || day > 31) {
            return false;
        }

        // checks valid month
        if (month < 1 || month > 12) {
            return false;
        }
        
        // keeps day at 2 numbers
        if (birthdate.charAt(2) != '-') {
            return false;
        }
        
        // keeps month at 2 numbers
        if (birthdate.charAt(5) != '-') {
            return false;
        }

        return true;

    }

    // checks license type is valid
    public boolean updateLicenseType(String newLicenseType) {
        // if years of experience is over 10, we cannot change type of license
        if (experienceYears > 10) {
            return false;
        }

        this.licenseType = newLicenseType;

        return true;
    }
}
