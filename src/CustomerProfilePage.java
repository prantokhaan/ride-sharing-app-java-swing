import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CustomerProfilePage extends JFrame {
    private Customer customer;

    public CustomerProfilePage(Customer customer) {
        this.customer = customer;
        setTitle("Customer Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and populate the profile information panel
        JPanel profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add JLabels to display customer information

        //  Display customer name
        JLabel nameLabel = new JLabel("Name: " + customer.getName());
        gbc.gridx = 0;
        gbc.gridy = 0;
        profilePanel.add(nameLabel, gbc);

        // Display customer email
        JLabel emailLabel = new JLabel("Email: " + customer.getEmail());
        gbc.gridx = 0;
        gbc.gridy = 1;
        profilePanel.add(emailLabel, gbc);

        // Display customer age
        JLabel ageLabel = new JLabel("Age: " + customer.getAge());
        gbc.gridx = 0;
        gbc.gridy = 2;
        profilePanel.add(ageLabel, gbc);

        // Display customer gender
        JLabel genderLabel = new JLabel("Gender: " + customer.getGender());
        gbc.gridx = 0;
        gbc.gridy = 3;
        profilePanel.add(genderLabel, gbc);

        // Display customer username
        JLabel usernameLabel = new JLabel("Username: " + customer.getUsername());
        gbc.gridx = 0;
        gbc.gridy = 4;
        profilePanel.add(usernameLabel, gbc);

        add(profilePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
