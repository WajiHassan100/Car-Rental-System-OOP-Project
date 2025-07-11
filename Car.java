public class Car {
    private String carID;
    private String make;
    private String model;
    private int year;
    private String color;
    private double rentalRate;
    private boolean isAvailable;

    public Car(String carID, String make, String model, int year, String color, double rentalRate) {
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.rentalRate = rentalRate;
        this.isAvailable = true;
    }

    public void rentCar() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Car rented successfully!");
        } else {
            System.out.println("Car is already rented.");
        }
    }

    public void returnCar() {
        isAvailable = true;
        System.out.println("Car returned successfully!");
    }

    public String getCarID() {
        return carID;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return carID + " - " + make + " " + model + " (" + year + ") - Rs" + rentalRate + "/day";
    }
}