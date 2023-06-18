import javax.swing.*;

import org.w3c.dom.events.EventException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RiderLoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPasswordButton;

    public RiderLoginPage() {
        setTitle("Rider Login");
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

        JLabel headingLabel = new JLabel("Rider Login");
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

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(loginButton, gbc);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (checkLogin(username, password)) {
                    try{
                        openRiderDashboard(username);
                    }catch(Exception ef){
                        System.out.println(ef.getMessage());
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });


        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(registerButton, gbc);
        registerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                openRegisterPage();
            }
        });

        forgotPasswordButton = new JButton("Forgot Password");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(forgotPasswordButton, gbc);

        backgroundLabel.add(formPanel);

        add(backgroundLabel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static boolean checkLogin(String username, String password) {
        try {
            FileInputStream fileIn = new FileInputStream("rider.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Rider rider;
            while (fileIn.available() > 0) {
                rider = (Rider) objectIn.readObject();
                if (rider.getUsername().equals(username) && rider.getPassword().equals(password)) {
                    objectIn.close();
                    fileIn.close();
                    return true;
                }
            }

            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void openRiderDashboard(String username) {
        RiderDashboard dashboard = new RiderDashboard(username);
        dispose();
    }

    private void openRegisterPage(){
        RiderRegistrationPage register = new RiderRegistrationPage();
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RiderLoginPage();
            }
        });
    }
}
