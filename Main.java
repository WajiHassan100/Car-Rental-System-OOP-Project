
public class Main {
    public static void main(String[] args) {
        RentalManagementSystem system = new RentalManagementSystem();

        // Load data from files on startup
        system.loadAllCarsFromFile();
        system.loadAllCustomersFromFile();
        system.loadAllRentalsFromFile();

        // Launch GUI
        javax.swing.SwingUtilities.invokeLater(() -> new RentalSystemGUI(system));

        // Add a shutdown hook to save data on exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            system.saveAllCarsToFile();
            system.saveAllCustomersToFile();
            system.saveAllRentalsToFile();
            System.out.println("Data saved before exit.");
        }));
    }
}
