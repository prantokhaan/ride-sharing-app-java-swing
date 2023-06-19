import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RiderDashboard extends JFrame {
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel balanceLabel;
    private JLabel ratingLabel;
    private JButton viewRequestsButton;
    private JButton profileButton;

    private List<Rider> riders;
    private Rider loggedInRider;

    public RiderDashboard(String username) {
        setTitle("Rider Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        nameLabel = new JLabel();
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(nameLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        emailLabel = new JLabel();
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(emailLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        balanceLabel = new JLabel();
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(balanceLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        ratingLabel = new JLabel();
        ratingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(ratingLabel, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());

        viewRequestsButton = new JButton("Pending Rides");
        viewRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform view requests action
                ViewAllRidesRiderPage pending = new ViewAllRidesRiderPage(loggedInRider, "ride.ser");
                pending.setVisible(true);
            }
        });
        buttonPanel.add(viewRequestsButton);

        profileButton = new JButton("Profile");
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRiderProfile();
            }
        });
        buttonPanel.add(profileButton);

        add(buttonPanel, gbc);

        loadRiders(); // Load riders from file
        findLoggedInRider(username); // Find logged-in rider

        if (loggedInRider != null) {
            nameLabel.setText("Welcome, " + loggedInRider.getName());
            emailLabel.setText("Email: " + loggedInRider.getEmail());
            balanceLabel.setText("Balance: " + loggedInRider.getBalance());
            ratingLabel.setText("Rating: " + loggedInRider.calculateAverageRating());
        } else {
            nameLabel.setText("Welcome");
            emailLabel.setText("");
            balanceLabel.setText("");
            ratingLabel.setText("");
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openRiderProfile() {
        RiderProfilePage profile = new RiderProfilePage(loggedInRider);
        dispose();
    }

    private void loadRiders() {
        try {
            FileInputStream fileIn = new FileInputStream("rider.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            riders = new ArrayList<>();
            Rider rider;
            while (fileIn.available() > 0) {
                rider = (Rider) objectIn.readObject();
                riders.add(rider);
            }

            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void findLoggedInRider(String username) {
        try {
            for (Rider rider : riders) {
                if (rider.getUsername().equals(username)) {
                    loggedInRider = rider;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RiderDashboard("exampleUsername");
            }
        });
    }
}
