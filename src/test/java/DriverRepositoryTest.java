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
 *   - Persisted driver updates  : tests 4, 5
 *   - Driver record counts      : tests 6, 7
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
     *  - must be at least two special chars between for characters 3 to 8 of ID
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

    // 4. persisted driver updates - D4 licence update rejected, original record preserved
    @Test
    void LicenceUpdateRejectedAndOriginalPreserved() {
        DriverRepository repo = newRepo();
        repo.add(new Driver(
            "4809DJ%@AH", 
            "David", 
            11, 
            "Heavy", 
            "11 | Main Street | Melbourne | Victoria | Australia", 
            "12-02-1964"
        ));

        Driver illegalUpdate = new Driver(
            "4809DJ%@AH", 
            "David", 
            11, 
            "Light", 
            "11 | Main Street | Melbourne | Victoria | Australia", 
            "12-02-1964"
        );

        assertFalse(repo.update(illegalUpdate));

        Driver reloaded = newRepo().retrieve("4809DJ%@AH");
        assertEquals("Heavy", reloaded.getLicenseType());
    }

    // 5. persisted driver updates - address change survives a fresh repo instance
    @Test
    void AddressUpdatePersistsAcrossRepoInstances() {
        DriverRepository writer = newRepo();
        writer.add(new Driver(
            "4869*&o3AH", 
            "Luke", 
            3, 
            "Light", 
            "50 | Main Street | Melbourne | Victoria | Australia", 
            "06-07-1969"
        ));

        Driver updated = new Driver(
            "4869*&o3AH", 
            "Luke", 
            3, 
            "Light", 
            "923 | Alternate Road | Sydney | New South Wales | Australia", 
            "06-07-1969"
        );

        assertTrue(writer.update(updated));

        Driver reloaded = newRepo().retrieve("4869*&o3AH");
        assertEquals("923 | Alternate Road | Sydney | New South Wales | Australia", reloaded.getAddress());
    }

    // 6. driver record counts - count accurately reflects successful adds to database
    @Test
    void DriverCountsAccuratelyReflectSuccessfulAdds() {
        DriverRepository repo = newRepo();
        assertEquals(0, repo.count());

        repo.add(new Driver(
            "4869*&o3AH", 
            "David", 
            3, 
            "Light", 
            "50 | Main Street | Melbourne | Victoria | Australia", 
            "06-07-1969"
        ));

        repo.add(new Driver(
            "4809DJ%@AH", 
            "Luke", 
            11, 
            "Heavy", 
            "11 | Main Street | Melbourne | Victoria | Australia", 
            "12-02-1964"
        ));

        repo.add(new Driver(
            "436851_$AD", 
            "Bob", 
            8, 
            "Light", 
            "123 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-1989"
        ));

        assertEquals(3, repo.count());
    }

    // 7. driver record counts - reject adds leave the repo content count unchanged
    @Test
    void RejectedDriverAddsDoNotIncrementRecordCount() {
        DriverRepository repo = newRepo();
        assertEquals(0, repo.count());
        
        repo.add(new Driver(
            "436851_$AD", 
            "Bob", 
            8, 
            "Light", 
            "123 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-1989"
        ));

        assertEquals(1, repo.count());

        // duplicate driverID
        assertFalse(repo.add(new Driver(
            "436851_$AD", 
            "Jacob", 
            3, 
            "Heavy", 
            "165 | Mall Street | Melbourne | Victoria | Australia", 
            "20-04-2001"
        )));

        // invalid driverID
        assertFalse(repo.add(new Driver(
            "84589323", 
            "Bob", 
            8, 
            "Light", 
            "123 | Main Street | Melbourne | Victoria | Australia", 
            "24-06-1989"
        )));

        assertEquals(1, repo.count());
    }

}
