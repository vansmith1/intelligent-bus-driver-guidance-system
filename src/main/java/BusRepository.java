import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONArray;
import org.json.JSONObject;


public class BusRepository {

    private static final String DEFAULT_PATH = "buses.json";
    private static final String ROOT_KEY = "buses";

    private final String databasePath;

    public BusRepository() {
        this(DEFAULT_PATH);
    }

    public BusRepository(String databasePath) {
        this.databasePath = databasePath;
    }

    // adds a bus to storage after validating B1 (format + uniqueness)
    public boolean add(Bus bus) {

        if (!bus.isValidBusID()) {
            System.err.println("Bus not added - invalid busID: " + bus.getBusId());
            return false;
        }

        JSONArray buses = loadBuses();

        for (int i = 0; i < buses.length(); i++) {
            if (buses.getJSONObject(i).getString("busID").equals(bus.getBusId())) {
                System.err.println("Bus not added - duplicate busID: " + bus.getBusId());
                return false;
            }
        }

        buses.put(toJSON(bus));
        saveBuses(buses);
        return true;
    }

    // retrieves a bus by busID, or null if no match exists
    public Bus retrieve(String busID) {

        JSONArray buses = loadBuses();

        for (int i = 0; i < buses.length(); i++) {
            JSONObject entry = buses.getJSONObject(i);
            if (entry.getString("busID").equals(busID)) {
                return fromJSON(entry);
            }
        }

        return null;
    }

    // replaces an existing bus, enforcing B2 against the stored capacity
    public boolean update(Bus updatedBus) {

        if (!updatedBus.isValidBusID()) {
            System.err.println("Bus not updated - invalid busID: " + updatedBus.getBusId());
            return false;
        }

        JSONArray buses = loadBuses();

        for (int i = 0; i < buses.length(); i++) {
            JSONObject entry = buses.getJSONObject(i);
            if (entry.getString("busID").equals(updatedBus.getBusId())) {

                Bus existing = fromJSON(entry);

                // B2: capacity can only decrease (or stay the same)
                if (!existing.canUpdateCapacity(updatedBus.getCapacity())) {
                    System.err.println("Update denied - B2: capacity cannot increase for busID "
                            + updatedBus.getBusId());
                    return false;
                }

                buses.put(i, toJSON(updatedBus));
                saveBuses(buses);
                return true;
            }
        }

        System.err.println("Bus not updated - busID not found: " + updatedBus.getBusId());
        return false;
    }

    // removes a bus by busID; returns false if no such record exists
    public boolean delete(String busID) {

        JSONArray buses = loadBuses();

        for (int i = 0; i < buses.length(); i++) {
            if (buses.getJSONObject(i).getString("busID").equals(busID)) {
                buses.remove(i);
                saveBuses(buses);
                return true;
            }
        }

        return false;
    }

    public int count() {
        return loadBuses().length();
    }

    // loads the JSON array from disk; returns an empty array if the file
    // does not exist yet or is blank (first-run / fresh-test scenario)
    private JSONArray loadBuses() {

        Path path = Paths.get(databasePath);

        if (!Files.exists(path)) {
            return new JSONArray();
        }

        try {
            String content = new String(Files.readAllBytes(path));
            if (content.isBlank()) {
                return new JSONArray();
            }
            return new JSONObject(content).getJSONArray(ROOT_KEY);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from: " + databasePath, e);
        }
    }

    // serialises the JSON array under {"buses": [...]} for human readability
    private void saveBuses(JSONArray buses) {

        JSONObject root = new JSONObject();
        root.put(ROOT_KEY, buses);

        try {
            Files.write(
                Paths.get(databasePath),
                root.toString(2).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to: " + databasePath, e);
        }
    }

    // bus -> JSON helper
    private JSONObject toJSON(Bus bus) {

        JSONObject entry = new JSONObject();

        entry.put("busID", bus.getBusId());
        entry.put("capacity", bus.getCapacity());
        entry.put("fuelLevel", bus.getFuelLevel());
        entry.put("fuelType", bus.getFuelType());

        return entry;
    }

    // JSON -> bus helper
    private Bus fromJSON(JSONObject entry) {
        return new Bus(
            entry.getString("busID"),
            entry.getInt("capacity"),
            entry.getDouble("fuelLevel"),
            entry.getString("fuelType")
        );
    }
}
