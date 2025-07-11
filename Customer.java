public class Customer extends User {
    private String customerID;
    private String licenseNumber;

    public Customer(String name, String email, String phone, String customerID, String licenseNumber) {
        super(name, email, phone);
        this.customerID = customerID;
        this.licenseNumber = licenseNumber;
    }

    @Override
    public void login() {
        System.out.println("Customer " + getName() + " logged in successfully.");
    }

    public void register() {
        System.out.println("Customer " + getName() + " registered successfully.");
    }

    public void viewAvailableCars(Car[] cars, int carCount) {
        System.out.println("Available Cars:");
        for (int i = 0; i < carCount; i++) {
            if (cars[i].isAvailable()) {
                System.out.println(cars[i]);
            }
        }
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}