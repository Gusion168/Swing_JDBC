import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

 class CustomerInfoApp extends JFrame {
    private JLabel idLabel, lastNameLabel, firstNameLabel, phoneLabel;
    private JTextField idField, lastNameField, firstNameField, phoneField;
    private JButton previousButton, nextButton;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public CustomerInfoApp() {
        setTitle("Customer Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        // Initialize components
        idLabel = new JLabel("ID:");
        lastNameLabel = new JLabel("Last Name:");
        firstNameLabel = new JLabel("First Name:");
        phoneLabel = new JLabel("Phone:");

        idField = new JTextField(10);
        lastNameField = new JTextField(20);
        firstNameField = new JTextField(20);
        phoneField = new JTextField(10);

        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");

        // Layout setup
        JPanel mainPanel = new JPanel(new GridLayout(5, 2));
        mainPanel.add(idLabel);
        mainPanel.add(idField);
        mainPanel.add(lastNameLabel);
        mainPanel.add(lastNameField);
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);
        mainPanel.add(previousButton);
        mainPanel.add(nextButton);

        // Add main panel to frame
        add(mainPanel);

        // Button action listeners
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPreviousCustomer();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showNextCustomer();
            }
        });

        // Initialize database connection and retrieve first customer
        initializeDatabase();
        showNextCustomer();

        setVisible(true);
    }

    // Method to initialize database connection
    private void initializeDatabase() {
        try {
            // Database connection details
            String DB_URL = "jdbc:mysql://localhost:3306/ExamJava";
            String USER = "root";
            String PASS = "Cheata168@";

            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database");
            System.exit(1);
        }
    }

    // Method to show next customer information
    private void showNextCustomer() {
        try {
            if (rs != null && rs.next()) {
                displayCustomerInfo(rs);
            } else {
                JOptionPane.showMessageDialog(this, "End of customer list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving customer information");
        }
    }

    // Method to show previous customer information
    private void showPreviousCustomer() {
        try {
            if (rs != null && rs.previous()) {
                displayCustomerInfo(rs);
            } else {
                JOptionPane.showMessageDialog(this, "Beginning of customer list");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving customer information");
        }
    }

    // Method to display customer information in fields
    private void displayCustomerInfo(ResultSet rs) {
        try {
            idField.setText(rs.getString("id"));
            lastNameField.setText(rs.getString("last_name"));
            firstNameField.setText(rs.getString("first_name"));
            phoneField.setText(rs.getString("phone"));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying customer information");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CustomerInfoApp();
            }
        });
    }
}
