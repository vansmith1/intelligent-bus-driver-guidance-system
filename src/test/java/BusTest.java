import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusTest {
    //-------------------------------------------------------
    //B1 Test Cases
    //-------------------------------------------------------
    
    //Valid ID - Normal Case
    @Test
    public void validBusIdShouldReturnPass(){

        Bus bus = new Bus("87654321", 40, 80.0, "Diesel");
        assertTrue(bus.isValidBusID());
    }
    //Invalid ID - ID Includes Letters
    @Test
    public void busIdWithLettersShouldFail(){

        Bus bus = new Bus("123456A7", 40, 80.0, "Diesel");
        assertFalse(bus.isValidBusID());
    }
    //Invalid ID - ID has less than 8 Digits
    @Test
    public void busIdWithLessThanEightDigitsShouldFail(){

        Bus bus = new Bus("1234567", 40, 80.0, "Diesel");
        assertFalse(bus.isValidBusID());
    }

    //B1 Extra Test Case - Invalid ID - ID has more than 8 Digits
    @Test
    public void busIdWithMoreThanEightDigitsShouldFail(){

        Bus bus = new Bus("123456789", 40, 80.0, "Diesel");
        assertFalse(bus.isValidBusID());
    }

    //-------------------------------------------------------
    //B2 Test Cases
    //-------------------------------------------------------

    //Valid Decrease In Capacity - Normal Case
    @Test
    public void busCapacityDecreaseShouldPass(){

        Bus bus = new Bus("12345678", 50, 80.0, "Electricity");
        assertTrue(bus.canUpdateCapacity(40));
    }
    //Invalid Increase - Capacity should not be increased 
    @Test
    public void busCapacityIncreaseShouldFail(){

        Bus bus = new Bus("12345678", 50, 80.0, "Electricity");
        assertFalse(bus.canUpdateCapacity(60));
    }
    //Capacity Stays The Same - Normal Case
    @Test
    public void busCapacityStaysTheSameShouldPass(){

        Bus bus = new Bus("12345678", 50, 80.0, "Electricity");
        assertTrue(bus.canUpdateCapacity(50));
    }
    //-------------------------------------------------------
    //B3 Test Cases
    //-------------------------------------------------------

    //Age > 50 Driver Operates Capacity < 50 Bus - Normal Case
    @Test
    public void driverOlderThanFiftyDrivesBusCapacityLessThanFiftyShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "12-12-1970");
        assertTrue(bus.canDriverOperateByAge(driver));
    }
    //Age > 50 Driver Operates Capacity > 50 Bus - Invalid Case
    @Test
    public void driverOlderThanFiftyDrivesBusCapacityGreaterThanFiftyShouldFail(){

        Bus bus = new Bus("12345678", 60, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "12-12-1970");
        assertFalse(bus.canDriverOperateByAge(driver));
    }
    //Age > 50 Driver Operates Capacity == 50 Bus - Invalid Case
    @Test
    public void driverOlderThanFiftyDrivesBusCapacityEqualToFiftyShouldFail(){

        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "12-12-1970");
        assertFalse(bus.canDriverOperateByAge(driver));
    }

    //B3 Extra Case - Age < 50 Driver Operates Capacity > 50 Bus - Normal Case
    @Test
    public void driverYoungerThanFiftyDrivesBusCapacityMoreThanFiftyShouldPass(){

        Bus bus = new Bus("12345678", 65, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "10-10-2000");
        assertTrue(bus.canDriverOperateByAge(driver));
    }
    //B3 Extra Case - Age < 50 Driver Operates Capacity < 50 Bus - Normal Case
    @Test
    public void driverYoungerThanFiftyDrivesBusCapacityLessThanFiftyShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "10-10-2000");
        assertTrue(bus.canDriverOperateByAge(driver));
    }
    //-------------------------------------------------------
    //B4 Test Cases
    //-------------------------------------------------------

    //Valid Experience (6) Requirement Driver Operates Electric Bus - Normal Case
    @Test
    public void driverMoreThanFiveYearsOfExperienceDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 6, null, null, null);
        assertTrue(bus.canDriverOperateElectricBus(driver));
    }
    //Invalid Experience (<5) Requirement Driver Operates Electric Bus - Invalid Case
    @Test
    public void driverLessThanFiveYearsOfExperienceDrivesElectricBusShouldFail(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 3, null, null, "12-12-1970");
        assertFalse(bus.canDriverOperateElectricBus(driver));
    }
    //Valid Experience (5) Requirement Driver Operates Electric Bus - Normal Case
    @Test
    public void driverFiveYearsOfExperienceDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 5, null, null, "12-12-1970");
        assertTrue(bus.canDriverOperateElectricBus(driver));
    }
    //-------------------------------------------------------
    //B5 Test Cases
    //-------------------------------------------------------

    //Valid Drivers Licensed Driver Operates Electric Bus - Normal Case
    @Test
    public void driverHoldsHeavyLicenseDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 0, "Heavy", null, null);
        assertTrue(bus.driverHasValidLicenseForFuelType(driver));
    }
    //Valid Drivers Licensed Driver Operates Electric Bus - Normal Case
    @Test
    public void driverHoldsPublicTransportLicenseDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 0, "PublicTransport", null, null);
        assertTrue(bus.driverHasValidLicenseForFuelType(driver));
    }
    //Invalid Drivers Licensed Driver Operates Hybrid Bus - Invalid Case
    @Test
    public void driverHoldsMediumLicenseDrivesHybridBusShouldFail(){

        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");
        Driver driver = new Driver(null, null, 0, "Medium", null, null);
        assertFalse(bus.driverHasValidLicenseForFuelType(driver));
    }
    //Invalid Drivers Licensed Driver Operates Hybrid Bus - Invalid Case
    @Test
    public void driverHoldsLightLicenseDrivesHybridBusShouldFail(){

        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");
        Driver driver = new Driver(null, null, 0, "Light", null, null);
        assertFalse(bus.driverHasValidLicenseForFuelType(driver));
    }
    //-------------------------------------------------------
    //B3, B4 and B5 Test together
    //-------------------------------------------------------
    
    //Valid Driver Operates Bus - Edge Case
    @Test 
    public void driverCanOperateBusShouldPass(){

        Bus bus = new Bus("23456789", 50, 80.0, "Hybrid");
        Driver driver = new Driver("34jdA_@HRF", "Nick", 6, "PublicTransport", "395|Bourke Street|Melbourne|Victoria|Australia", "27-05-2006");
        assertTrue(bus.canOperateByDriver(driver));
    }
    //-------------------------------------------------------
}