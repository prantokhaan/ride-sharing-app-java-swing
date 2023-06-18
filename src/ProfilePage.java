import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePage extends JFrame {
    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel genderLabel;
    private JLabel phoneNumberLabel;
    private JLabel ageLabel;

    public ProfilePage(Customer customer) {
        setTitle("Profile Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        usernameLabel = new JLabel("Username: " + customer.getUsername());
        gbc.gridx = 0;
        gbc.gridy = 0;
        profilePanel.add(usernameLabel, gbc);

        nameLabel = new JLabel("Name: " + customer.getName());
        gbc.gridx = 0;
        gbc.gridy = 1;
        profilePanel.add(nameLabel, gbc);

        emailLabel = new JLabel("Email: " + customer.getEmail());
        gbc.gridx = 0;
        gbc.gridy = 2;
        profilePanel.add(emailLabel, gbc);

        genderLabel = new JLabel("Gender: " + customer.getGender());
        gbc.gridx = 0;
        gbc.gridy = 3;
        profilePanel.add(genderLabel, gbc);

        phoneNumberLabel = new JLabel("Phone Number: " + customer.getPhoneNumber());
        gbc.gridx = 0;
        gbc.gridy = 4;
        profilePanel.add(phoneNumberLabel, gbc);

        ageLabel = new JLabel("Age: " + customer.getAge());
        gbc.gridx = 0;
        gbc.gridy = 5;
        profilePanel.add(ageLabel, gbc);

        JButton closeButton = new JButton("Close");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        profilePanel.add(closeButton, gbc);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(profilePanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
