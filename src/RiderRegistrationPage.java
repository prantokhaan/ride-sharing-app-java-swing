import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RiderRegistrationPage extends JFrame {
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private JButton forgotPasswordButton;
    private JComboBox<String> genderComboBox;
    private JTextField ageField;
    private JTextField nidField;
    private JTextField drivingLicenseField;
    private JTextField bikeLicenseField;

    public RiderRegistrationPage() {
        setTitle("Rider Registration");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("bike_image.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel headingLabel = new JLabel("Register as a Rider");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(headingLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(passwordField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(genderLabel, gbc);

        String[] genderOptions = {"Male", "Female"};
        genderComboBox = new JComboBox<>(genderOptions);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(genderComboBox, gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(ageLabel, gbc);

        ageField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(ageField, gbc);

        JLabel nidLabel = new JLabel("NID Number:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(nidLabel, gbc);

        nidField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(nidField, gbc);

        JLabel drivingLicenseLabel = new JLabel("Driving License Number:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(drivingLicenseLabel, gbc);

        drivingLicenseField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(drivingLicenseField, gbc);

        JLabel bikeLicenseLabel = new JLabel("Bike License Number:");
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(bikeLicenseLabel, gbc);

        bikeLicenseField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 9;
        formPanel.add(bikeLicenseField, gbc);

        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(registerButton, gbc);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String gender = genderComboBox.getSelectedItem().toString();
                int age = Integer.parseInt(ageField.getText());
                String nidNumber = nidField.getText();
                String drivingLicenseNumber = drivingLicenseField.getText();
                String bikeLicenseNumber = bikeLicenseField.getText();
                String phoneNumber = "018"; // Set the phone number according to your requirements

                Rider rider = new Rider(name, email, password, username, gender, age, nidNumber,
                        drivingLicenseNumber, bikeLicenseNumber, phoneNumber);

                saveRider(rider, "rider.ser");

                JOptionPane.showMessageDialog(null, "Registration successful");
            }
        });

        loginButton = new JButton("Already Registered?");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(loginButton, gbc);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RiderLoginPage login = new RiderLoginPage();
                dispose();
            }
        });

        forgotPasswordButton = new JButton("Forgot Password");
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(forgotPasswordButton, gbc);

        backgroundLabel.add(formPanel);

        add(backgroundLabel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static void saveRider(Rider rider, String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(rider);
            objectOut.close();
            fileOut.close();
            System.out.println("Rider object saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RiderRegistrationPage();
            }
        });
    }
}
