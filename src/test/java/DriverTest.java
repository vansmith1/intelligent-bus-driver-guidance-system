import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DriverTest {
    // D1
    // normal case
    @Test
    void validDriverIDShouldPass() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "null", 
            10, 
            "light", 
            "72|Brown Street|Melbourne|Victoria|Australia", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidDriverID());
    }

    // invalid case
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

    // invalid case
    // @Test // COME BACK AFTER DATABASE
    // void driverIDNotUniqueShouldFail() {
    //     Driver busDriver = new Driver(
    //         "34jdA_@HRF", 
    //         "Vanessa", 
    //         10, 
    //         "light", 
    //         ""72|Brown Street|Melbourne|Victoria|Australia", 
    //         "25-05-2005"
    //     );
    //     assertTrue(busDriver.isValidDriverID());
    // }

    // invalid case
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

    // invalid case
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

    // invalid case
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

    // invalid case
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

    // edge case
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

    // edge case
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
    // normal case
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

    // invalid case
    @Test
    void addressInWrongFormatShouldFail() {
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

    // invalid case
    @Test
    void addressWithMissingFieldShouldFail() {
        Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "light", 
            "72||Melbourne|Victoria|",
            "25-05-2005"
        );
        assertFalse(busDriver.isValidAddress());
    }

    // D3
    // normal case
    @Test
    void birthdateInRightFormatShouldPass() {
         Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "light", 
            "72|Brown Street|Melbourne|Victoria", 
            "25-05-2005"
        );
        assertTrue(busDriver.isValidBirthdate());
    }

    // invalid case
    @Test
    void birthdateInWrongFormatShouldFail() {
         Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "light", 
            "72|Brown Street|Melbourne|Victoria", 
            "05-2005"
        );
        assertFalse(busDriver.isValidBirthdate());
    }

    // invalid case
    @Test
    void birthdateWithInvalidDayShouldFail() {
         Driver busDriver = new Driver(
            "34jdA_@HRF", 
            "Vanessa", 
            10, 
            "light", 
            "72|Brown Street|Melbourne|Victoria", 
            "32-05-2005"
        );
        assertFalse(busDriver.isValidBirthdate());
    }

    // WAIT FOR DATABASE
    // D4
    // normal case
    @Test 
    void updateLicenseTypeShouldPass() {}

    // invalid case
    @Test 
    void updateLicenseTypeWithOverTenYearsExperienceShouldFail() {}

    // edge case
    @Test
    void updateLicenseTypeWithExactlyTenYearsExperienceShouldPass() {}

    // D5
    // invalid case
    @Test 
    void modifyDriverIDShouldFail() {}

    // invalid case
    @Test 
    void modifyNameShouldFail() {}

    // edge case
    @Test
    void updateLicenseTypeShouldNotChangeDriverIDOrName() {}
}