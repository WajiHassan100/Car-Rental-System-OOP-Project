import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private String rentalID;
    private Customer customer;
    private Car car;
    private String rentalDate;
    private String returnDate;
    private double totalAmount;

public Rental(String rentalID, Customer customer, Car car, String rentalDate, String returnDate) {
        this.rentalID = rentalID;
        this.customer = customer;
        this.car = car;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalAmount = calculateTotal();
    }

    public String getRentalID() {
        return rentalID;
    }

    public void setRentalID(String rentalID) {
        this.rentalID = rentalID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private double calculateTotal() {
    // Parse rentalDate and returnDate to LocalDate
    LocalDate startDate = LocalDate.parse(rentalDate);
    LocalDate endDate = LocalDate.parse(returnDate);
    
    // Calculate the number of days between the dates (inclusive of start and end dates)
    long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Add 1 to include both start and end days
    
    // Ensure the number of days is at least 1
    if (days < 1) {
        days = 1;
    }

    return car.getRentalRate() * days;
}

    public void createRental() {
        
    if (!car.isAvailable()) {
        throw new IllegalStateException("Car is already rented!");
    }
    car.rentCar(); 
    System.out.println("Car rented successfully!"); // Consider removing or making conditional
}

    public void returnRental() {
        car.returnCar();
        System.out.println("Rental returned: " + rentalID);
    }

    @Override
    public String toString() {
        return rentalID + " - " + customer.getName() + " rented " + car.getMake() + " (Due: " + returnDate + ")";
    }

}
