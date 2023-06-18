import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDashboard extends JFrame {
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JButton bookRideButton;
    private JButton viewRideButton;
    private JButton profileButton;

    private List<Customer> customers;
    private List<Rider>riders;
    private Customer loggedInCustomer;

    public CustomerDashboard(String username) {
        setTitle("Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(nameLabel, BorderLayout.NORTH);

        emailLabel = new JLabel();
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(emailLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        bookRideButton = new JButton("Book A Ride");
        bookRideButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        loadRiders();
        BookRidePage bookRidePage = new BookRidePage(loggedInCustomer, riders);
        bookRidePage.setVisible(true);
    }
});
        buttonPanel.add(bookRideButton);

        viewRideButton = new JButton("View Rides");
        viewRideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform make order action
                try{
                    ViewAllRidesPage customerRides = new ViewAllRidesPage(loggedInCustomer, "ride.ser");
                customerRides.setVisible(true);
                }catch(Exception ef){
                    System.out.println(ef.getMessage());
                }

                
            }
        });
        buttonPanel.add(viewRideButton);

        profileButton = new JButton("Profile");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCustomerProfile();
            }
        });
        buttonPanel.add(profileButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadCustomers(); // Load customers from file
        findLoggedInCustomer(username); // Find logged-in customer

        if (loggedInCustomer != null) {
            nameLabel.setText("Welcome, " + loggedInCustomer.getName());
            emailLabel.setText("Email: " + loggedInCustomer.getEmail());
        } else {
            nameLabel.setText("Welcome");
            emailLabel.setText("");
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openCustomerProfile() {
        ProfilePage profile = new ProfilePage(loggedInCustomer);
        dispose();
    }

    private void loadCustomers() {
        try {
            FileInputStream fileIn = new FileInputStream("customer.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            customers = new ArrayList<>();
            Customer customer;
            while (fileIn.available() > 0) {
                customer = (Customer) objectIn.readObject();
                customers.add(customer);
            }

            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadRiders(){
        try{
            FileInputStream fileInRider = new FileInputStream("rider.ser");
            ObjectInputStream objectInRider = new ObjectInputStream(fileInRider);

            riders = new ArrayList<>();
            Rider rider;
            while(fileInRider.available()>0){
                rider = (Rider) objectInRider.readObject();
                riders.add(rider);
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void findLoggedInCustomer(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                loggedInCustomer = customer;
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CustomerDashboard("exampleUsername");
            }
        });
    }
}
