import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ViewAllRidesRiderPage extends JFrame {
    private JPanel ridesPanel;
    private Rider rider;
    private String filename;

    public ViewAllRidesRiderPage(Rider rider, String filename) {
        this.rider = rider;
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

                // Check if the ride is booked by the logged-in rider
                if (ride.getRider().getUsername().equals(rider.getUsername())) {
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

                JButton acceptButton = new JButton("Accept");
                acceptButton.addActionListener(new AcceptButtonListener(ride));

                JButton reachedButton = new JButton("Reached");
                reachedButton.addActionListener(new ReachedButtonListener(ride));

                JPanel buttonPanel = new JPanel();
                if(ride.getRideStatus().equals("Pending") || ride.getRideStatus().equals("pending")){
                    buttonPanel.add(acceptButton);
                }else if(ride.getRideStatus().equals("Accepted")){
                    buttonPanel.remove(acceptButton);
                    buttonPanel.add(reachedButton);
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

    private class AcceptButtonListener implements ActionListener {
        private Ride ride;

        public AcceptButtonListener(Ride ride) {
            this.ride = ride;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            acceptRide(ride);
        }
    }
    private class ReachedButtonListener implements ActionListener {
        private Ride ride;

        public ReachedButtonListener(Ride ride) {
            this.ride = ride;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ReachedRider(ride);
        }
    }

    private void acceptRide(Ride ride) {
        // Perform actions when the accept button is clicked
        // For example, you can update the ride status or perform other necessary operations
        ride.changeRideStatus("Accepted");

        // Update the ride status in the ride.ser file
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            List<Ride> updatedRides = new ArrayList<>();
            Ride existingRide;
            while (fileIn.available() > 0) {
                existingRide = (Ride) objectIn.readObject();
                if (existingRide.getStartTime().equals(ride.getStartTime())) {
                    existingRide.changeRideStatus("Accepted");
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
        JOptionPane.showMessageDialog(this, "Ride Accepted!", "Ride Acceptance", JOptionPane.INFORMATION_MESSAGE);
    }
    private void ReachedRider(Ride ride) {
        // Perform actions when the accept button is clicked
        // For example, you can update the ride status or perform other necessary operations
        ride.changeRideStatus("Reached to the Location");

        // Update the ride status in the ride.ser file
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            List<Ride> updatedRides = new ArrayList<>();
            Ride existingRide;
            while (fileIn.available() > 0) {
                existingRide = (Ride) objectIn.readObject();
                if (existingRide.getStartTime().equals(ride.getStartTime())) {
                    existingRide.changeRideStatus("Reached to the Location");
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

    public static void main(String[] args) {
        Rider rider = new Rider(); // Replace with your own rider object
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewAllRidesRiderPage(rider, "ride.ser");
            }
        });
    }
}
