import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewAllRidesPage extends JFrame {
    private JTextArea ridesTextArea;
    private Customer customer;
    private JPanel ridesPanel;
    private String filename;

    public ViewAllRidesPage(Customer customer, String filename) {
        this.customer = customer;
        this.filename = filename;

        setTitle("View All Rides");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        ridesPanel = new JPanel();
        ridesPanel.setLayout(new BoxLayout(ridesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(ridesPanel);
        add(scrollPane, BorderLayout.CENTER);

        loadRides(filename);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadRides(String filename) {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            List<Ride> rides = new ArrayList<>();
            Ride ride;
            while (fileIn.available() > 0) {
                ride = (Ride) objectIn.readObject();

                // Check if the ride is booked by the logged-in customer
                if (ride.getCustomer().getUsername().equals(customer.getUsername())) {
                    rides.add(ride);
                }
            }

            objectIn.close();
            fileIn.close();

            displayRides(rides);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayRides(List<Ride> rides) {
        if (rides.isEmpty()) {
            JLabel noRidesLabel = new JLabel("No rides available.");
            ridesPanel.add(noRidesLabel);
        } else {
            for (Ride ride : rides) {
                JPanel ridePanel = new JPanel();
                ridePanel.setLayout(new BoxLayout(ridePanel, BoxLayout.Y_AXIS));

                JLabel customerLabel = new JLabel("Customer: " + ride.getCustomer().getName());
                JLabel riderLabel = new JLabel("Rider: " + ride.getRider().getName());
                JLabel startTimeLabel = new JLabel("Start Time: " + ride.getStartTime().toString());
                JLabel sourceLabel = new JLabel("Source Location: " + ride.getSourceLocation());
                JLabel destinationLabel = new JLabel("Destination Location: " + ride.getDestinationLocation());
                JLabel distanceLabel = new JLabel("Distance: " + ride.getDistance() + " units");
                JLabel fareLabel = new JLabel("Fare: $" + ride.getFare());
                JLabel rideStatusLabel = new JLabel("Ride Status: " + ride.getRideStatus());

                JButton reachedDestinationButton = new JButton("Finish");
                reachedDestinationButton.addActionListener(new ReachedDestinationButtonListener(ride));

                JButton rateRiderButton = new JButton("Rate the Rider");
                rateRiderButton.addActionListener(new RateRiderButtonListener(ride));

                JPanel buttonPanel = new JPanel();
                if (ride.getRideStatus().equals("Reached to the Location")) {
                    buttonPanel.add(reachedDestinationButton);
                } else if (ride.getRideStatus().equals("Ride Finished")) {
                    buttonPanel.add(rateRiderButton);
                }

                ridePanel.add(customerLabel);
                ridePanel.add(riderLabel);
                ridePanel.add(startTimeLabel);
                ridePanel.add(sourceLabel);
                ridePanel.add(destinationLabel);
                ridePanel.add(distanceLabel);
                ridePanel.add(fareLabel);
                ridePanel.add(rideStatusLabel);
                ridePanel.add(buttonPanel);

                ridesPanel.add(ridePanel);
                ridesPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing between rides
            }
        }
    }

    private class ReachedDestinationButtonListener implements ActionListener {
        private Ride ride;

        public ReachedDestinationButtonListener(Ride ride) {
            this.ride = ride;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            reachedDestinationRider(ride);
        }
    }

    private class RateRiderButtonListener implements ActionListener {
        private Ride ride;

        public RateRiderButtonListener(Ride ride) {
            this.ride = ride;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int rating = Integer.parseInt(JOptionPane.showInputDialog("Enter a rating (1-5):"));
            if (rating >= 1 && rating <= 5) {
                ride.getRider().addRating(rating);
                updateRiderRating(ride.getRider());

                JOptionPane.showMessageDialog(ViewAllRidesPage.this, "Rating added successfully!", "Rating", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ViewAllRidesPage.this, "Invalid rating entered!", "Rating Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reachedDestinationRider(Ride ride) {
        // Perform actions when the accept button is clicked
        // For example, you can update the ride status or perform other necessary operations
        ride.changeRideStatus("Ride Finished");

        // Update the ride status in the ride.ser file
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            List<Ride> updatedRides = new ArrayList<>();
            Ride existingRide;
            while (fileIn.available() > 0) {
                existingRide = (Ride) objectIn.readObject();
                if (existingRide.getStartTime().equals(ride.getStartTime())) {
                    existingRide.changeRideStatus("Ride Finished");
                }
                updatedRides.add(existingRide);
            }

            objectIn.close();
            fileIn.close();

            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            for (Ride updatedRide : updatedRides) {
                objectOut.writeObject(updatedRide);
            }

            objectOut.close();
            fileOut.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        // Show a message indicating that the ride has been accepted
        JOptionPane.showMessageDialog(this, "Rider Reached!", "Ride Acceptance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateRiderRating(Rider rider) {
        try {
            FileInputStream fileIn = new FileInputStream("rider.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            List<Rider> updatedRiders = new ArrayList<>();
            Rider existingRider;
            while (fileIn.available() > 0) {
                existingRider = (Rider) objectIn.readObject();
                if (existingRider.getUsername().equals(rider.getUsername())) {
                    existingRider = rider;
                }
                updatedRiders.add(existingRider);
            }

            objectIn.close();
            fileIn.close();

            FileOutputStream fileOut = new FileOutputStream("rider.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            for (Rider updatedRider : updatedRiders) {
                objectOut.writeObject(updatedRider);
            }

            objectOut.close();
            fileOut.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer(); // Replace with your own customer object
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewAllRidesPage(customer, "ride.ser");
            }
        });
    }
}
