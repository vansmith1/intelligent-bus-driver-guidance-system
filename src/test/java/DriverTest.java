import org.junit.jupiter.api.Test;

public class DriverTest {
    // D1
    // normal case
    @Test
    void validDriverIDShouldPass() {}

    // invalid case
    @Test
    void driverIDTooShortShouldFail() {}

    // invalid case
    @Test 
    void driverIDNotUniqueShouldFail() {}

    // invalid case
    @Test
    void driverIDTooLongShouldFail() {}

    // invalid case
    @Test
    void driverIDStartingWithOneShouldFail() {}

    // invalid case
    @Test
    void driverIDWithOneSpecialCharacterShouldFail() {}

    // invalid case
    @Test
    void driverIDEndingWithLowercaseLettersShouldFail() {}

    // invalid case
    @Test
    void driverIDWithExactlyTenCharactersShouldPass() {}

    // edge case
    @Test
    void driverIDWithExactlyTwoSpecialCharactersShouldPass() {}

    // D2
    // normal case
    @Test
    void addressInRightFormatShouldPass() {}

    // invalid case
    @Test
    void addressInWrongFormatShouldFail() {}

    // invalid case
    @Test
    void addressWithMissingFieldShouldFail() {}

    // D3
    // normal case
    @Test
    void birthdateInRightFormatShouldPass() {}

    // invalid case
    @Test
    void birthdateInWrongFormatShouldFail() {}

    // invalid case
    @Test
    void birthdateWithInvalidDayShouldFail() {}

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