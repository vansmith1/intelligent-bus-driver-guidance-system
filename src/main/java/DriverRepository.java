import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONArray;
import org.json.JSONObject;

public class DriverRepository {
    
    private static final String DATABASE_PATH = "drivers.json";

    public boolean add(Driver driver) {

        if (!driver.isValidDriverID()) {
            System.err.println("Driver not added - invalid driverID: " + driver.getDriverID());
            return false;
        } else if (!driver.isValidBirthdate()) {
            System.err.println("Driver not added - invalid birthdate: " + driver.getBirthdate());
            return false;
        } else if (!driver.isValidAddress()) {
            System.err.println("Driver not added - invalid address: " + driver.getAddress());
            return false;
        }

        JSONArray drivers = loadDrivers();

        for (int i = 0; i < drivers.length(); i++) {
            if (drivers.getJSONObject(i).getString("driverID").equals(driver.getDriverID())) {
                System.err.println("Driver not addded - duplicate driverID " + driver.getDriverID());
                return false;
            }
        }

        drivers.put(toJSON(driver));
        saveDrivers(drivers);
        return true;
    }

    public Driver retrieve(String driverID) {
        JSONArray drivers = loadDrivers();
        for (int i = 0; i < drivers.length(); i++) {
            JSONObject driver = drivers.getJSONObject(i);
            if (driver.getString("driverID").equals(driverID)) {
                return fromJSON(driver);
            }
        }
        return null;
    }

    public boolean update(Driver updatedDriver) {

        if (!updatedDriver.isValidBirthdate()) {
            System.err.println("Driver not added - invalid birthdate");
            return false;
        } else if (!updatedDriver.isValidAddress()) {
            System.err.println("Driver not added - invalid address");
            return false;
        }

        JSONArray drivers = loadDrivers();
        boolean found = false;

        for (int i = 0; i < drivers.length(); i++) {
            JSONObject driver = drivers.getJSONObject(i);
            if (driver.getString("driverID").equals(updatedDriver.getDriverID())) {
                Driver currentDriver = fromJSON(driver);
                if (currentDriver.getExperienceYears() > 10 && !currentDriver.getLicenseType().equals(updatedDriver.getLicenseType())) {
                    System.err.println("Update denied - experience exceeds 10 years");
                    return false;
                }
                Driver safeUpdatedDriver = new Driver(
                    currentDriver.getDriverID(),       
                    currentDriver.getName(),          
                    updatedDriver.getExperienceYears(),
                    updatedDriver.getLicenseType(),
                    updatedDriver.getAddress(),
                    updatedDriver.getBirthdate()
                );
                drivers.put(i, toJSON(safeUpdatedDriver));
                found = true;
                break;
            }
        }

        if (!found) {
            System.err.println("driverID not found: " + updatedDriver.getDriverID());
            return false;
        }

        saveDrivers(drivers);
        return true;
    }

    public int count() {
        return loadDrivers().length();
    }

    // handles loading the file content into a JSONArray
    private JSONArray loadDrivers() {

        Path path = Paths.get(DATABASE_PATH);

        if (!Files.exists(path)) {
            return new JSONArray();
        } try {
            String driver = new String(Files.readAllBytes(path));
            return new JSONObject(driver).getJSONArray("drivers");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from: " + DATABASE_PATH, e);
        }
    }

    // handles saving newly updaated drivers JSONArray to JSON file
    private void saveDrivers(JSONArray drivers) {

        JSONObject driverObject = new JSONObject();
        driverObject.put("drivers", drivers);

        try {
            Files.write(Paths.get(DATABASE_PATH), driverObject.toString(2).getBytes(),
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to: " + DATABASE_PATH, e);
        }
    }

    // driver to JSON helper
    private JSONObject toJSON(Driver d) {

        JSONObject driver = new JSONObject();

        driver.put("driverID", d.getDriverID());
        driver.put("name", d.getName());
        driver.put("experienceYears", d.getExperienceYears());
        driver.put("licenseType", d.getLicenseType());
        driver.put("address", d.getAddress());
        driver.put("birthdate", d.getBirthdate());

        return driver;
    }

    // JSON to driver helper
    private Driver fromJSON(JSONObject o) {
        return new Driver(
            o.getString("driverID"),
            o.getString("name"),
            o.getInt("experienceYears"),
            o.getString("licenseType"),
            o.getString("address"),
            o.getString("birthdate")
        );
    }
}