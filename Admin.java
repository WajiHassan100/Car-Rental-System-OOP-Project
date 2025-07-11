public class Admin extends User {
    private String adminID;
    private String username;
    private String password;

    public Admin(String name, String email, String phone, String adminID, String username, String password) {
        super(name, email, phone);
        this.adminID = adminID;
        this.username = username;
        this.password = password;
    }

    public Admin(String username, String password) {
    super("Default Admin", "default@admin.com", "0000000000");
    this.username = username;
    this.password = password;
}

    @Override
    public void login() {
        System.out.println("Admin " + username + " logged in.");
    }

    public void addCar(Car[] cars, int carCount, Car newCar) {
        if (carCount < cars.length) {
            cars[carCount] = newCar;
            System.out.println("Car added successfully!");
        } else {
            System.out.println("Cannot add more cars. Inventory full.");
        }
    }

    public void removeCar(Car[] cars, int carCount, String carID) {
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getCarID().equals(carID)) {
                cars[i] = cars[carCount - 1];// Replacing the car to be removed with the last car in the array
                cars[carCount - 1] = null;// Setting the last position to null to remove the reference
                System.out.println("Car removed successfully!");
                return;
            }
        }
        System.out.println("Car not found.");
    }

    public void viewAllRentals(Rental[] rentals, int rentalCount) {
        System.out.println("All Rentals:");
        for (int i = 0; i < rentalCount; i++) {
            System.out.println(rentals[i]);
        }
    }

    public String getAdminID() {
        return adminID;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);//this.username stored wala
    }
}