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
    private JButton viewRequestsButton;
    private JButton profileButton;

    private List<Rider> riders;
    private Rider loggedInRider;

    public RiderDashboard(String username) {
        setTitle("Rider Dashboard");
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

        balanceLabel = new JLabel();
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(balanceLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        viewRequestsButton = new JButton("View Requests");
        viewRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform view requests action
                JOptionPane.showMessageDialog(null, "View Requests action performed");
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

        add(buttonPanel, BorderLayout.SOUTH);

        loadRiders(); // Load riders from file
        findLoggedInRider(username); // Find logged-in rider

        if (loggedInRider != null) {
            nameLabel.setText("Welcome, " + loggedInRider.getName());
            emailLabel.setText("Email: " + loggedInRider.getEmail());
            balanceLabel.setText("Balance: " + loggedInRider.getBalance());
        } else {
            nameLabel.setText("Welcome");
            emailLabel.setText("");
            balanceLabel.setText("");
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
        try{
            for (Rider rider : riders) {
            if (rider.getUsername().equals(username)) {
                loggedInRider = rider;
                break;
            }
        }
        }catch(Exception e){
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
