import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DriverTest {
    // D1
    // normal case for valid id
    @Test
    void validDriverIDShouldPass() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
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
            "light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "32-05-2005"
        );
        assertFalse(busDriver.isValidBirthdate());
    }

    // D4
    // normal case for license type
    @Test 
    void updateLicenseTypeShouldPass() {
        Driver driver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            5, 
            "light",
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(driver.updateLicenseType("Heavy"));
        assertEquals("Heavy", driver.getLicenseType());
    }

    // invalid case - ensuring if experience is over ten years, license type cannot be updated
    @Test 
    void updateLicenseTypeWithOverTenYearsExperienceShouldFail() {
    Driver driver = new Driver(
        "34jdA_@HRF", 
        "Vanessa", 
        11, 
        "light",
        "72|Brown Street|Melbourne|Victoria|Australia", 
        "25-05-2005"
    );
    assertFalse(driver.updateLicenseType("Heavy"));
    assertEquals("light", driver.getLicenseType());
    }

    // edge case - updating license type
    @Test
    void updateLicenseTypeWithExactlyTenYearsExperienceShouldPass() {
            Driver driver = new Driver(
        "34jdA_@HRF", 
        "Vanessa", 
        10, 
        "light",
        "72|Brown Street|Melbourne|Victoria|Australia", 
        "25-05-2005"
    );
    assertTrue(driver.updateLicenseType("Heavy"));
    assertEquals("Heavy", driver.getLicenseType());
    }

    // D5 - ensuring license type update doesnt change id or name
    @Test
    void updateLicenseTypeShouldNotChangeDriverIDOrName() {
        Driver driver = new Driver(
            "34jdA_@HRF",
            "Vanessa",
            10,
            "light",
            "72|Brown Street|Melbourne|Victoria|Australia",
            "25-05-2005"
        );
        driver.updateLicenseType("Heavy");

        assertEquals("34jdA_@HRF", driver.getDriverID());
        assertEquals("Vanessa", driver.getName());
    }
}