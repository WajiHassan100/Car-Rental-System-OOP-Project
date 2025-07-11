import javax.swing.*; // Import Swing for GUI components like JOptionPane
import java.io.*;      // Import classes for file I/O operations
import java.util.Scanner; // Import Scanner for reading files

public class RentalManagementSystem {
    // Arrays to store cars, customers, and rentals
    private Car[] cars;
    private Customer[] customers;
    private Rental[] rentals;
    
    private int carCount;
    private int customerCount;
    private int rentalCount;

    // Constructor: Initializes arrays and counts
    public RentalManagementSystem() {
        cars = new Car[100];            // Can store up to 100 cars
        customers = new Customer[100];  // Can store up to 100 customers
        rentals = new Rental[100];      
        carCount = 0;
        customerCount = 0;
        rentalCount = 0;
    }

    // Adds a new customer to the array
    public void addCustomer(Customer customer) {
        if (customerCount < customers.length) {
            customers[customerCount++] = customer; // Increment count after adding
        }
    }

    // Saves all customer records to a file
    public void saveAllCustomersToFile() {
        try (FileWriter writer = new FileWriter("customers.txt")) {
            for (int i = 0; i < customerCount; i++) {
                Customer c = customers[i];
                writer.write(c.getCustomerID() + "," + c.getName() + "," + c.getEmail() + "," + c.getPhone() + "," + c.getLicenseNumber());
                writer.write(System.lineSeparator()); // Use system-specific line separator
            }
        } catch (IOException e) {
            System.out.println("Error saving all customers: " + e.getMessage());
        }
    }

    public void loadAllCustomersFromFile() {
        try (Scanner sc = new Scanner(new File("customers.txt"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] data = line.split(",");
                if (data.length == 5) {
                    String customerID = data[0];
                    // Avoid duplicates
                    if (findCustomer(customerID) == null) {
                        Customer customer = new Customer(data[1], data[2], data[3], customerID, data[4]);
                        customers[customerCount++] = customer;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }


    public void addCar(Car car) {
        if (carCount < cars.length) {
            cars[carCount++] = car;
        }
    }

    public void saveAllCarsToFile() {
        try (FileWriter writer = new FileWriter("cars.txt", false)) {
            for (int i = 0; i < carCount; i++) {
                Car car = cars[i];
                writer.write(car.getCarID() + "," + car.getMake() + "," + car.getModel() + "," + car.getYear() + "," + car.getColor() + "," + car.getRentalRate() + "," + car.isAvailable());
                writer.write(System.lineSeparator()); // Use system-specific line separator
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving cars: " + e.getMessage());
        }
    }

    // Loads all cars from file
    public void loadAllCarsFromFile() {
        try (Scanner scanner = new Scanner(new File("cars.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 7) {
                    String carID = data[0];
                    // Avoid duplicates
                    if (findCar(carID) == null) {
                        Car car = new Car(carID, data[1], data[2], Integer.parseInt(data[3]), data[4], Double.parseDouble(data[5]));
                        if (!Boolean.parseBoolean(data[6])) { //  // Check if the car is NOT available (i.e., it is already rented according to the file)
                            System.out.println("Loading car " + carID + " as rented"); 
                            car.rentCar(); // This line prints "Car rented successfully!" if isAvailable is true initially
                        }
                        cars[carCount++] = car;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading cars: " + e.getMessage());
        }
    }

    // Removes a car by its ID
    public void removeCar(String carID) {
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getCarID().equals(carID)) {
                cars[i] = cars[carCount - 1]; // Replace with last element
                cars[carCount - 1] = null;    // Nullify last spot
                carCount--;                   // Decrease car count
                saveAllCarsToFile();          // Save updated list to file
                JOptionPane.showMessageDialog(null, "Car removed successfully!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Car not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Adds a rental to the array
    public void addRental(Rental rental) {
        if (rentalCount < rentals.length) {
            rentals[rentalCount++] = rental;
        }
    }

    // Saves all rental records to a file
    public void saveAllRentalsToFile() {
        try (FileWriter writer = new FileWriter("rentals.txt")) {
            for (int i = 0; i < rentalCount; i++) {
                Rental r = rentals[i];
                writer.write(r.getRentalID() + "," + r.getCustomer().getCustomerID() + "," + r.getCar().getCarID() + "," + r.getRentalDate() + "," + r.getReturnDate() + "," + r.getTotalAmount());
                writer.write(System.lineSeparator()); // Use system-specific line separator
            }
        } catch (IOException e) {
            System.out.println("Error saving all rentals: " + e.getMessage());
        }
    }

    // Loads all rentals from file
    public void loadAllRentalsFromFile() {
        try (Scanner scanner = new Scanner(new File("rentals.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 6) { //rentalid,customerid,carid,rdate,returndate,total
                    String rentalID = data[0];
                    if (findRental(rentalID) == null) { // Skip duplicates
                        Customer customer = findCustomer(data[1]);
                        Car car = findCar(data[2]);
                        if (customer != null && car != null && car.isAvailable()) { 
                            Rental rental = new Rental(rentalID, customer, car, data[3], data[4]);
                            rentals[rentalCount++] = rental; 
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading rentals: " + e.getMessage());
        }
    }

    // Authenticates admin credentials
  public Admin authenticateAdmin(String username, String password) {
    if (username.equals("waji") && password.equals("oop123")) {
        return new Admin(username, password);
    } else {
        return null;
    }
}

    // Finds a customer by ID
    public Customer findCustomer(String customerID) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getCustomerID().equals(customerID)) {
                return customers[i];
            }
        }
        return null;
    }

    // Finds a car by ID
    public Car findCar(String carID) {
        for (int i = 0; i < carCount; i++) {
            if (cars[i].getCarID().equals(carID)) {
                return cars[i];
            }
        }
        return null;
    }

    // Returns only cars that are available
    public Car[] getAvailableCars() {
        int count = 0;
        for (int i = 0; i < carCount; i++) {
            if (cars[i] != null && cars[i].isAvailable()) {
                count++;
            }
        }

        Car[] availableCars = new Car[count];//SIZE COUNT
        int index = 0;
        for (int i = 0; i < carCount; i++) {
            if (cars[i] != null && cars[i].isAvailable()) {
                availableCars[index++] = cars[i];
            }
        }
        return availableCars;
    }

    public Rental findRental(String rentalID) {
        for (int i = 0; i < rentalCount; i++) {
            if (rentals[i].getRentalID().equals(rentalID)) {
                return rentals[i];
            }
        }
        return null;
    }

    // Displays all rentals in a dialog
   public void viewAllRentals() {
    String result = "";
    for (int i = 0; i < rentalCount; i++) {
        result += rentals[i] + "\n";
    }
    JOptionPane.showMessageDialog(null, result);
}

    // Returns current list of cars (trimmed array)
    public Car[] getCars() {
        Car[] currentCars = new Car[carCount];
        System.arraycopy(cars, 0, currentCars, 0, carCount);
        return currentCars;
    }

    // Returns current list of rentals (trimmed array)
    public Rental[] getRentals() {
        Rental[] currentRentals = new Rental[rentalCount];
        System.arraycopy(rentals, 0, currentRentals, 0, rentalCount);
        return currentRentals;
    }
}