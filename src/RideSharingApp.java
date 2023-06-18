import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RideSharingApp extends JFrame {
    private JButton customerButton;
    private JButton riderButton;

    public RideSharingApp() {
        setTitle("Ride Sharing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the frame size to 500x500 pixels
        setSize(500, 500);

        // Create a panel for holding the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        customerButton = new JButton("Customer");
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement customer button functionality
                openLoginPage();
            }
        });

        riderButton = new JButton("Rider");
        riderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement rider button functionality
                openRiderLoginPage();
            }
        });

        // Add buttons to the panel
        buttonPanel.add(customerButton);
        buttonPanel.add(riderButton);

        // Add the panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openLoginPage() {
        LoginPage loginPage = new LoginPage();
        dispose();
    }

    private void openRiderLoginPage(){
        RiderLoginPage riderLogin = new RiderLoginPage();
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RideSharingApp();
            }
        });
    }
}
