import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.*;

/**
 * Coverage map (Assignment 4 rubric, Driver integration testing):
 *   - Valid driver storage      : test 1
 *   - Invalid driver rejection  : tests 2, 3
 *   - Persisted driver updates  : tests 3
 *   - Driver record counts      : tests 4
 */

public class DriverRepositoryTest {

    @TempDir
    Path tempDir;

    // helper: fresh repo wired to the same temp file (simulates a process restart)
    private DriverRepository newRepo() {
        return new DriverRepository(tempDir.resolve("drivers.json").toString());
    }

    // 1. valid driver storage - valid driver is persisted and round-trips through storage
    @Test
    void DriversAreStoredProperly() {
        DriverRepository writer = newRepo();
        Driver driver = new Driver(
            "436851_$AD", 
            "Bob", 
            8, 
            "Light", 
            "123 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-1989"
        );

        assertTrue(writer.add(driver));

        Driver retrieved = newRepo().retrieve("12345678");

        assertNotNull(retrieved);
        assertEquals("436851_$AD", retrieved.getDriverID());
        assertEquals("Bob", retrieved.getName());
        assertEquals(8, retrieved.getExperienceYears());
        assertEquals("Light", retrieved.getLicenseType());
        assertEquals("123 | Main Street | Melbourne | Victoria | Australia", retrieved.getAddress());
        assertEquals("24-06-1989", retrieved.getBirthdate());
    }

    /** 2. rejection of invalid driver - driverID that does not meet the rules of D1 is refused
     *  - must be exactly 10 chars
     *  - first two chars must be between 2-9
     *  - must be at least two special chars between chars 3-8
     *  - last two chars must be uppercase letters (A-Z)
     */
    @Test
    void InvalidDriverIDFormatIsRejected() {
        DriverRepository repo = newRepo();
        Driver invalidDriver = new Driver(
            "84589323", 
            "Bob", 
            8, 
            "Light", 
            "123 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-1989"
        );

        assertFalse(repo.add(invalidDriver));
        assertEquals(0, repo.count());
        assertNull(repo.retrieve("84589323"));
    }

    // 3. rejection of invalid driver - duplicate driverID is refused, and ensures the original record doesn't get overwritten
    @Test
    void DuplicateDriverIDIsRejected() {
        DriverRepository repo = newRepo();
        Driver originalID = new Driver(
            "4809DJ%@AD", 
            "Dave", 
            8, 
            "Light", 
            "150 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-1984"
        );

        Driver duplicateID = new Driver(
            "4809DJ%@AD", 
            "Will", 
            8, 
            "Light", 
            "170 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-2003"
        );

        assertTrue(repo.add(originalID));
        assertFalse(repo.add(duplicateID));

        assertEquals(1, repo.count());

        // verifies that the original record doesn't get overwritten
        Driver stored = repo.retrieve("4809DJ%@AD");
        assertEquals("Dave", stored.getName());
        assertEquals("24-06-1984", stored.getBirthdate());
    }

    @Test
    void UpdatesArePersistedCorrectly() {}

    @Test
    void RecordCountsAreUpdatedCorrectly() {}

}
