public class Bus {

    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType;

    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {

        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType; // Diesel, Hybrid, Electricity

    }

    //Getters
    public String getBusId(){
        return busID;
    }

    public int getCapacity(){
        return capacity;
    }

        public double getFuelLevel() {
        return fuelLevel;
    }

    public String getFuelType() {
        return fuelType;
    }

    //B1 - Bus ID must be exactly 8 digits and unique
    //Uniqueness will be validated by BusRepository when storing buses later on.
     public boolean isValidBusID() {
        return busID != null && busID.matches("\\d{8}");
    }

    //B2 - Bus capacity cannot increase during operative hours. However, it can decrease.
    public boolean canUpdateCapacity(int newCapacity){
        return newCapacity <= capacity;
    }

    //B3 - If driver age > 50, they cannot drive buses with > 50 capacity.
    public boolean canDriverOperateByAge(Driver driver){
        return !(driver.getAge() > 50 && capacity >= 50);
    }

    //B4 - Only drivers with at least 5 years of experience can drive electric buses
    public boolean canDriverOperateElectricBus(Driver driver){
        if(fuelType.equals("Electricity")){
            return driver.getExperienceYears() >= 5;
        }
        return true;
    }

    //B5 - Only Heavy or PublicTransport licence holders can operate electric and hybrid buses
    public boolean driverHasValidLicenseForFuelType(Driver driver){
        if(fuelType.equals("Electricity") || fuelType.equals("Hybrid")){
            String license = driver.getLicenseType();
            return license != null &&
                   (license.equals("Heavy") || license.equals("PublicTransport"));
        }
        return true;
    }

    //Checks for B3, B4 and B5 together
    public boolean canOperateByDriver(Driver driver){
        return canDriverOperateByAge(driver)
            && canDriverOperateElectricBus(driver)
            && driverHasValidLicenseForFuelType(driver);
    }

}   