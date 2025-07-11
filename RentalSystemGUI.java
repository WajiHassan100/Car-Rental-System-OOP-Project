import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RentalSystemGUI {
    private JFrame frame;
    private RentalManagementSystem system;

    public RentalSystemGUI(RentalManagementSystem system) {
        this.system = system;
        system.loadAllCarsFromFile();
        system.loadAllCustomersFromFile();
        system.loadAllRentalsFromFile();
        initMainMenu();
    }

    private void initMainMenu() {
        frame = new JFrame("Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new GradientPanel(new Color(230, 240, 250), new Color(230, 240, 250));
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Welcome to Car Rental System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(0, 60, 100));
        panel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 25));
        buttonPanel.setOpaque(false);

        JButton adminLogin = createStyledButton("Admin Login");
        adminLogin.addActionListener(e -> handleAdminLogin());

        JButton customerRegister = createStyledButton("Customer Register");
        customerRegister.addActionListener(e -> handleCustomerRegister());

        JButton customerLogin = createStyledButton("Customer Login");
        customerLogin.addActionListener(e -> handleCustomerLogin());

        JButton exit = createStyledButton("Exit");
        exit.addActionListener(e -> System.exit(0));

        JButton[] buttons = {adminLogin, customerRegister, customerLogin, exit};
        for (int i = 0; i < buttons.length; i++) {
        buttonPanel.add(buttons[i]);
}
        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 94, 132));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        return button;
    }

    private void handleAdminLogin() {
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        loginPanel.setBackground(new Color(230, 240, 250));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        loginPanel.add(createStyledLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(createStyledLabel("Password:"));
        loginPanel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(frame, loginPanel, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) return;

            Admin admin = system.authenticateAdmin(username, password);
            if (admin != null) {
                showAdminMenu(admin);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleCustomerRegister() {
        JPanel registerPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        registerPanel.setBackground(new Color(230, 240, 250));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField idField = new JTextField();
        JTextField licenseField = new JTextField();

        registerPanel.add(createStyledLabel("Name:"));
        registerPanel.add(nameField);
        registerPanel.add(createStyledLabel("Email:"));
        registerPanel.add(emailField);
        registerPanel.add(createStyledLabel("Phone:"));
        registerPanel.add(phoneField);
        registerPanel.add(createStyledLabel("Customer ID:"));
        registerPanel.add(idField);
        registerPanel.add(createStyledLabel("License No:"));
        registerPanel.add(licenseField);

        int result = JOptionPane.showConfirmDialog(frame, registerPanel, "Register Customer", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || 
                    phoneField.getText().isEmpty() || idField.getText().isEmpty()) {
                    throw new IllegalArgumentException("All fields are required!");
                }

                Customer customer = new Customer(
                    nameField.getText(), 
                    emailField.getText(), 
                    phoneField.getText(), 
                    idField.getText(), 
                    licenseField.getText()
                );
                system.addCustomer(customer);
                system.saveAllCustomersToFile();
                JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleCustomerLogin() {
        JTextField customerIDField = new JTextField();
        JPanel loginPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        loginPanel.setBackground(new Color(230, 240, 250));
        loginPanel.add(createStyledLabel("Customer ID:"));
        loginPanel.add(customerIDField);

        int result = JOptionPane.showConfirmDialog(frame, loginPanel, "Customer Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String customerID = customerIDField.getText().trim();
            if (customerID.isEmpty()) return;

            Customer customer = system.findCustomer(customerID);
            if (customer != null) {
                showCustomerMenu(customer);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Customer ID!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAdminMenu(Admin admin) {
        JFrame adminFrame = new JFrame("Admin Menu");
        adminFrame.setSize(500, 400);
        adminFrame.setLocationRelativeTo(frame);

        JPanel panel = new GradientPanel(new Color(230, 240, 250), new Color(230, 240, 250));
        panel.setLayout(new GridLayout(4, 1, 0, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JButton addCar = createStyledButton("Add Car");
        addCar.addActionListener(e -> {
            JPanel carPanel = new JPanel(new GridLayout(6, 2, 15, 15));
            carPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            carPanel.setBackground(new Color(230, 240, 250));
            JTextField id = new JTextField(), make = new JTextField(), 
                      model = new JTextField(), year = new JTextField(), 
                      color = new JTextField(), rate = new JTextField();

            carPanel.add(createStyledLabel("Car ID:"));
            carPanel.add(id);
            carPanel.add(createStyledLabel("Make:"));
            carPanel.add(make);
            carPanel.add(createStyledLabel("Model:"));
            carPanel.add(model);
            carPanel.add(createStyledLabel("Year:"));
            carPanel.add(year);
            carPanel.add(createStyledLabel("Color:"));
            carPanel.add(color);
            carPanel.add(createStyledLabel("Daily Rate:"));
            carPanel.add(rate);

            int confirm = JOptionPane.showConfirmDialog(adminFrame, carPanel, "Add New Car", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                try {
                    Car car = new Car(
                        id.getText(), 
                        make.getText(), 
                        model.getText(),
                        Integer.parseInt(year.getText()), 
                        color.getText(), 
                        Double.parseDouble(rate.getText())
                    );
                    system.addCar(car);
                    system.saveAllCarsToFile();
                    JOptionPane.showMessageDialog(adminFrame, "Car Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(adminFrame, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton removeCar = createStyledButton("Remove Car");
        removeCar.addActionListener(e -> {
            JTextField carIDField = new JTextField();
            JPanel removePanel = new JPanel(new GridLayout(1, 2, 15, 15));
            removePanel.setBackground(new Color(230, 240, 250));
            removePanel.add(createStyledLabel("Car ID:"));
            removePanel.add(carIDField);

            int confirm = JOptionPane.showConfirmDialog(adminFrame, removePanel, "Remove Car", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                String carID = carIDField.getText().trim();
                if (!carID.isEmpty()) {
                    system.removeCar(carID);
                }
            }
        });

        JButton viewRentals = createStyledButton("View Rentals");
        viewRentals.addActionListener(e -> system.viewAllRentals());

        JButton logout = createStyledButton("Logout");
        logout.addActionListener(e -> adminFrame.dispose());

        panel.add(addCar);
        panel.add(removeCar);
        panel.add(viewRentals);
        panel.add(logout);

        adminFrame.add(panel);
        adminFrame.setVisible(true);
    }

    private void showCustomerMenu(Customer customer) {
        JFrame custFrame = new JFrame("Customer Menu");
        custFrame.setSize(500, 500);
        custFrame.setLocationRelativeTo(frame);

        JPanel panel = new GradientPanel(new Color(230, 240, 250), new Color(230, 240, 250));
        panel.setLayout(new GridLayout(5, 1, 0, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JButton viewCars = createStyledButton("View Available Cars");
        viewCars.addActionListener(e -> {
            Car[] availableCars = system.getAvailableCars();
            if (availableCars.length == 0) {
                JOptionPane.showMessageDialog(custFrame, "No available cars.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("<html><h3>Available Cars:</h3>");
                for (Car car : availableCars) {
                    sb.append(car.toString().replace("\n", "<br>")).append("<br>");
                }
                sb.append("</html>");
                JOptionPane.showMessageDialog(custFrame, sb.toString(), "Available Cars", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton rentCar = createStyledButton("Rent a Car");
        rentCar.addActionListener(e -> {
            JPanel rentPanel = new JPanel(new GridLayout(5, 2, 15, 15));
            rentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            rentPanel.setBackground(new Color(230, 240, 250));
            JTextField rentalID = new JTextField();
            JTextField carID = new JTextField();
            JTextField rentalDate = new JTextField(LocalDate.now().toString());
            JTextField returnDate = new JTextField(LocalDate.now().plusDays(1).toString());
            JTextField paymentMethod = new JTextField("Credit Card");

            rentPanel.add(createStyledLabel("Rental ID:"));
            rentPanel.add(rentalID);
            rentPanel.add(createStyledLabel("Car ID:"));
            rentPanel.add(carID);
            rentPanel.add(createStyledLabel("Rental Date:"));
            rentPanel.add(rentalDate);
            rentPanel.add(createStyledLabel("Return Date:"));
            rentPanel.add(returnDate);
            rentPanel.add(createStyledLabel("Payment Method:"));
            rentPanel.add(paymentMethod);

            int confirm = JOptionPane.showConfirmDialog(custFrame, rentPanel, "Rent Car", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                try {
                    Car car = system.findCar(carID.getText());
                    if (car != null && car.isAvailable()) {
                        Rental rental = new Rental(
                            rentalID.getText(), 
                            customer, 
                            car, 
                            rentalDate.getText(), 
                            returnDate.getText()
                        );
                        
                        Payment payment = new Payment(
                            "PAY-" + rentalID.getText(),
                            rentalID.getText(),
                            rental.getTotalAmount(),
                            LocalDate.now().toString(),
                            paymentMethod.getText()
                        );
                        payment.processPayment();
                        
                        system.addRental(rental);
                        car.rentCar();
                        system.saveAllCarsToFile();
                        system.saveAllRentalsToFile();
                        
                        JOptionPane.showMessageDialog(custFrame, 
                            "Rental Successful!\n Your Payment Amount is: Rs" + rental.getTotalAmount(), 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(custFrame, "Car not available.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(custFrame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton returnCar = createStyledButton("Return a Car");
        returnCar.addActionListener(e -> {
            JTextField rentalIDField = new JTextField();
            JPanel returnPanel = new JPanel(new GridLayout(1, 2, 15, 15));
            returnPanel.setBackground(new Color(230, 240, 250));
            returnPanel.add(createStyledLabel("Rental ID:"));
            returnPanel.add(rentalIDField);

            int confirm = JOptionPane.showConfirmDialog(custFrame, returnPanel, "Return Car", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                String rentalID = rentalIDField.getText().trim();
                if (!rentalID.isEmpty()) {
                    Rental rental = system.findRental(rentalID);
                    if (rental != null) {
                        rental.getCar().returnCar();
                        system.saveAllCarsToFile();
                        JOptionPane.showMessageDialog(custFrame, "Car Returned Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(custFrame, "Rental not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton makePayment = createStyledButton("Make Payment");
        makePayment.addActionListener(e -> {
            JTextField rentalIDField = new JTextField();
            JPanel paymentPanel = new JPanel(new GridLayout(1, 2, 15, 15));
            paymentPanel.setBackground(new Color(230, 240, 250));
            paymentPanel.add(createStyledLabel("Rental ID:"));
            paymentPanel.add(rentalIDField);

            int confirm = JOptionPane.showConfirmDialog(custFrame, paymentPanel, "Make Payment", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                String rentalID = rentalIDField.getText().trim();
                if (!rentalID.isEmpty()) {
                    Rental rental = system.findRental(rentalID);
                    if (rental != null) {
                        String[] methods = {"Credit Card", "Debit Card", "Cash"};
                        String method = (String) JOptionPane.showInputDialog(
                            custFrame, 
                            "Select payment method:", 
                            "Payment", 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            methods, 
                            methods[0]
                        );
                        
                        if (method != null) {
                            Payment payment = new Payment(
                                "PAY-" + System.currentTimeMillis(),
                                rentalID,
                                rental.getTotalAmount(),
                                LocalDate.now().toString(),
                                method
                            );
                            payment.processPayment();
                            JOptionPane.showMessageDialog(custFrame, 
                                "Payment of $" + rental.getTotalAmount() + " processed via " + method, 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(custFrame, "Rental not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton logout = createStyledButton("Logout");
        logout.addActionListener(e -> custFrame.dispose());

        panel.add(viewCars);
        panel.add(rentCar);
        panel.add(returnCar);
        panel.add(makePayment);
        panel.add(logout);

        custFrame.add(panel);
        custFrame.setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(0, 60, 100));
        return label;
    }
}

// Custom panel for gradient background
class GradientPanel extends JPanel {
    private Color color1;
    private Color color2;

    public GradientPanel(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}