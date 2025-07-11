public class Payment {
    private String paymentID;
    private String rentalID;
    private double amount;

    private String paymentMethod;

    public Payment(String paymentID, String rentalID, double amount, String paymentDate, String paymentMethod) {
        this.paymentID = paymentID;
        this.rentalID = rentalID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public void processPayment() {
        System.out.println("Payment of Rs" + amount + " processed via " + paymentMethod);
    }

    @Override
    public String toString() {
        return paymentID + " - Rental: " + rentalID + ", Amount: Rs" + amount;
    }
}