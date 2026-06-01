import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusTest {

    //B1 Test Cases
    @Test
    public void validBusIdShouldReturnPass(){

        Bus bus = new Bus("87654321", 40, 80.0, "Diesel");
        assertTrue(bus.isValidBusID());
    }

    @Test
    public void busIdWithLettersShouldFail(){

        Bus bus = new Bus("123456A7", 40, 80.0, "Diesel");
        assertFalse(bus.isValidBusID());
    }

    @Test
    public void busIdWithLessThanEightDigitsShouldFail(){

        Bus bus = new Bus("1234567", 40, 80.0, "Diesel");
        assertFalse(bus.isValidBusID());
    }

    //B1 Extra Test Case
    @Test
    public void busIdWithMoreThanEightDigitsShouldFail(){

        Bus bus = new Bus("123456789", 40, 80.0, "Diesel");
        assertFalse(bus.isValidBusID());
    }

    //B2 Test Cases
    @Test
    public void busCapacityDecreaseShouldPass(){

        Bus bus = new Bus("12345678", 50, 80.0, "Electricity");
        assertTrue(bus.canUpdateCapacity(40));
    }

    @Test
    public void busCapacityIncreaseShouldFail(){

        Bus bus = new Bus("12345678", 50, 80.0, "Electricity");
        assertFalse(bus.canUpdateCapacity(60));
    }

    @Test
    public void busCapacityStaysTheSameShouldPass(){

        Bus bus = new Bus("12345678", 50, 80.0, "Electricity");
        assertTrue(bus.canUpdateCapacity(50));
    }

    //B3 Test Cases
    @Test
    public void driverOlderThanFiftyDrivesBusCapacityLessThanFiftyShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "12-12-1970");
        assertTrue(bus.canDriverOperateByAge(driver));
    }

    @Test
    public void driverOlderThanFiftyDrivesBusCapacityGreaterThanFiftyShouldFail(){

        Bus bus = new Bus("12345678", 60, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "12-12-1970");
        assertFalse(bus.canDriverOperateByAge(driver));
    }

    @Test
    public void driverOlderThanFiftyDrivesBusCapacityEqualToFiftyShouldFail(){

        Bus bus = new Bus("12345678", 50, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "12-12-1970");
        assertFalse(bus.canDriverOperateByAge(driver));
    }

    //B3 Extra Cases
    @Test
    public void driverYoungerThanFiftyDrivesBusCapacityMoreThanFiftyShouldPass(){

        Bus bus = new Bus("12345678", 65, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "10-10-2000");
        assertTrue(bus.canDriverOperateByAge(driver));
    }

    @Test
    public void driverYoungerThanFiftyDrivesBusCapacityLessThanFiftyShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");
        Driver driver = new Driver(null, null, 0, null, null, "10-10-2000");
        assertTrue(bus.canDriverOperateByAge(driver));
    }

    //B4 Test Cases
    @Test
    public void driverMoreThanFiveYearsOfExperienceDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 6, null, null, null);
        assertTrue(bus.canDriverOperateElectricBus(driver));
    }

    @Test
    public void driverLessThanFiveYearsOfExperienceDrivesElectricBusShouldFail(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 3, null, null, "12-12-1970");
        assertFalse(bus.canDriverOperateElectricBus(driver));
    }

    @Test
    public void driverFiveYearsOfExperienceDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 5, null, null, "12-12-1970");
        assertTrue(bus.canDriverOperateElectricBus(driver));
    }

    //B5 Test Cases
    @Test
    public void driverHoldsHeavyLicenseDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 0, "Heavy", null, null);
        assertTrue(bus.driverHasValidLicenseForFuelType(driver));
    }

    @Test
    public void driverHoldsPublicTransportLicenseDrivesElectricBusShouldPass(){

        Bus bus = new Bus("12345678", 40, 80.0, "Electricity");
        Driver driver = new Driver(null, null, 0, "PublicTransport", null, null);
        assertTrue(bus.driverHasValidLicenseForFuelType(driver));
    }

    @Test
    public void driverHoldsMediumLicenseDrivesHybridBusShouldFail(){

        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");
        Driver driver = new Driver(null, null, 0, "Medium", null, null);
        assertFalse(bus.driverHasValidLicenseForFuelType(driver));
    }

    @Test
    public void driverHoldsLightLicenseDrivesHybridBusShouldFail(){

        Bus bus = new Bus("12345678", 40, 80.0, "Hybrid");
        Driver driver = new Driver(null, null, 0, "Light", null, null);
        assertFalse(bus.driverHasValidLicenseForFuelType(driver));
    }
    
    //B3, B4 and B5 Test together
    @Test 
    public void driverCanOperateBusShouldPass(){

        Bus bus = new Bus("23456789", 50, 80.0, "Hybrid");
        Driver driver = new Driver("34jdA_@HRF", "Nick", 6, "PublicTransport", "395|Bourke Street|Melbourne|Victoria|Australia", "27-05-2006");
        assertTrue(bus.canOperateByDriver(driver));
    }
}