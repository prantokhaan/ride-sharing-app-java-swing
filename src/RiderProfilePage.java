import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiderProfilePage extends JFrame {
    private JLabel usernameLabel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel genderLabel;
    private JLabel phoneNumberLabel;
    private JLabel ageLabel;
    private JLabel approvalStatusLabel;
    private JLabel nidLabel;
    private JLabel drivingLabel;
    private JLabel bikeLicenseLabel;

    public RiderProfilePage(Rider rider) {
        setTitle("Profile Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        usernameLabel = new JLabel("Username: " + rider.getUsername());
        gbc.gridx = 0;
        gbc.gridy = 0;
        profilePanel.add(usernameLabel, gbc);

        nameLabel = new JLabel("Name: " + rider.getName());
        gbc.gridx = 0;
        gbc.gridy = 1;
        profilePanel.add(nameLabel, gbc);

        emailLabel = new JLabel("Email: " + rider.getEmail());
        gbc.gridx = 0;
        gbc.gridy = 2;
        profilePanel.add(emailLabel, gbc);

        genderLabel = new JLabel("Gender: " + rider.getGender());
        gbc.gridx = 0;
        gbc.gridy = 3;
        profilePanel.add(genderLabel, gbc);

        phoneNumberLabel = new JLabel("Phone Number: " + rider.getPhoneNumber());
        gbc.gridx = 0;
        gbc.gridy = 4;
        profilePanel.add(phoneNumberLabel, gbc);

        ageLabel = new JLabel("Age: " + rider.getAge());
        gbc.gridx = 0;
        gbc.gridy = 5;
        profilePanel.add(ageLabel, gbc);

        approvalStatusLabel = new JLabel("Approval Status: " + rider.getApprovalStatus());
        gbc.gridx = 0;
        gbc.gridy = 6;
        profilePanel.add(approvalStatusLabel, gbc);

        nidLabel = new JLabel("Nid No: " + rider.getNid());
        gbc.gridx = 0;
        gbc.gridy = 7;
        profilePanel.add(nidLabel, gbc);

        drivingLabel = new JLabel("Driving License: " + rider.getDrivingLicense());
        gbc.gridx = 0;
        gbc.gridy = 8;
        profilePanel.add(drivingLabel, gbc);

        bikeLicenseLabel = new JLabel("Bike License: " + rider.getBikeLicense());
        gbc.gridx = 0;
        gbc.gridy = 9;
        profilePanel.add(bikeLicenseLabel, gbc);

        JButton closeButton = new JButton("Close");
        gbc.gridx = 0;
        gbc.gridy = 10;
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
