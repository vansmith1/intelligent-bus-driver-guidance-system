import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DriverTest {

    @TempDir
    Path tempDir;

    private DriverRepository newRepo() {
        return new DriverRepository(tempDir.resolve("drivers.json").toString());
    }
    // D1
    // normal case for valid id
    @Test
    void validDriverIDShouldPass() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidDriverID());
    }

    // invalid case - testing id is too short
    @Test
    void driverIDTooShortShouldFail() {
            Driver busDriver = new Driver(
            "34jdA_@HR", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertFalse(busDriver.isValidDriverID());
    }

    // invalid case - testing id is too long
    @Test
    void driverIDTooLongShouldFail() {
        Driver busDriver = new Driver(
            "34jdA_@HRFA", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertFalse(busDriver.isValidDriverID());
    }

    // invalid case - id starts with 1
    @Test
    void driverIDStartingWithOneShouldFail() {
        Driver busDriver = new Driver(
            "14jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertFalse(busDriver.isValidDriverID());
    }

    // invalid case - id only has one special character
    @Test
    void driverIDWithOneSpecialCharacterShouldFail() {
        Driver busDriver = new Driver(
            "34jdA2@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertFalse(busDriver.isValidDriverID());
    }

    // invalid case - id ends in lower case letter
    @Test
    void driverIDEndingWithLowercaseLettersShouldFail() {
        Driver busDriver = new Driver(
            "34jdA_@HRf", 
            "Vanessa", 
            10, 
            "light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertFalse(busDriver.isValidDriverID());
    }

    // edge case - id has ten characters
    @Test
    void driverIDWithExactlyTenCharactersShouldPass() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidDriverID());
    }

    // edge case - id has two special characters
    @Test
    void driverIDWithExactlyTwoSpecialCharactersShouldPass() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidDriverID());
    }

    // D2
    // normal case for address
    @Test
    void addressInRightFormatShouldPass() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidAddress());
    }

    // invalid case - address in bad format
    @Test
    void addressInWrongFormatShouldFail() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72 Brown Street Melbourne Victoria Australia", 
            "25-05-2005"
        );
        assertFalse(busDriver.isValidAddress());
    }

    // invalid case - not all the fields being in address
    @Test
    void addressWithMissingFieldShouldFail() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria",
            "25-05-2005"
        );
        assertFalse(busDriver.isValidAddress());
    }

    // D3
    // normal case for birthdate
    @Test
    void birthdateInRightFormatShouldPass() {
         Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidBirthdate());
    }

    // invalid case - birthdate in bad format
    @Test
    void birthdateInWrongFormatShouldFail() {
         Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia",
            "05-2005"
        );
        assertFalse(busDriver.isValidBirthdate());
    }

    // invalid case - birthdate with day that doesn't exist
    @Test
    void birthdateWithInvalidDayShouldFail() {
         Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "Light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "32-05-2005"
        );
        assertFalse(busDriver.isValidBirthdate());
    }

    // D4
    // normal case for license type
    @Test 
    void updateLicenseTypeShouldPass() {
        DriverRepository repo = newRepo();

        Driver driver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            5, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        repo.add(driver);

        Driver updatedDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            5, 
            "Heavy",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(repo.update(updatedDriver));
    }

    // invalid case - ensuring if experience is over ten years, license type cannot be updated
    @Test 
    void updateLicenseTypeWithOverTenYearsExperienceShouldFail() {
        DriverRepository repo = newRepo();

        Driver driver = new Driver(
            "78xy!@ABCD", 
            "Vanessa", 
            11, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        repo.add(driver);

        Driver updatedDriver = new Driver(
            "78xy!@ABCD", 
            "Vanessa", 
            11, 
            "Heavy",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertFalse(repo.update(updatedDriver));
    }

    // edge case - updating license type
    @Test
    void updateLicenseTypeWithExactlyTenYearsExperienceShouldPass() {
        DriverRepository repo = newRepo();

        Driver driver = new Driver(
            "93pq#$LMNO", 
            "Vanessa", 
            10, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        repo.add(driver);

        Driver updatedDriver = new Driver(
            "93pq#$LMNO", 
            "Vanessa", 
            10, 
            "Heavy",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(repo.update(updatedDriver));
    }

    // D5 - normal case ensuring name doesnt update
    @Test
    void updateShouldNotChangeName() {
        DriverRepository repo = newRepo();

        Driver driver = new Driver(
            "56ab#@CDXY", 
            "Vanessa", 
            5, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        repo.add(driver);

        Driver updatedDriver = new Driver(
            "56ab#@CDXY", 
            "Julia", 
            5, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        repo.update(updatedDriver);
        Driver result = repo.retrieve("56ab#@CDXY");

        assertEquals("Vanessa", result.getName());
    }

    // normal case - ensuring id doesnt update
    @Test
    void updateShouldNotChangeID() {
        DriverRepository repo = newRepo();

        Driver driver = new Driver(
            "34jeA_@HRF", 
            "Vanessa", 
            5, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        repo.add(driver);

        Driver updatedDriver = new Driver(
            "34jdA_@HTR", 
            "Vanessa", 
            5, 
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );

        assertFalse(repo.update(updatedDriver));
    }

    // edge case - changing id, name, and license type.
    @Test
    void updateLicenseTypeShouldNotChangeDriverIDOrName() {
        DriverRepository repo = newRepo();

        Driver driver = new Driver(
            "35jdA_@HRF",
            "Vanessa",
            5,
            "Light",
            "72|Brown Street|Melbourne|Victoria|Australia",
            "25-05-2005"
        );

        repo.add(driver);

        Driver updatedDriver = new Driver(
            "33jdA_@HTR",  
            "Julia",       
            5,
            "Heavy",
            "72|Brown Street|Melbourne|Victoria|Australia",
            "25-05-2005"
        );
        assertFalse(repo.update(updatedDriver));

        Driver result = repo.retrieve("35jdA_@HRF");

        assertEquals("35jdA_@HRF", result.getDriverID());
        assertEquals("Vanessa", result.getName());
    }
}