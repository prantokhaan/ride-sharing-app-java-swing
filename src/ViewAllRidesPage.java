import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewAllRidesPage extends JFrame {
    private JTextArea ridesTextArea;
    private Customer customer;

    public ViewAllRidesPage(Customer customer, String filename) {
        this.customer = customer;

        setTitle("View All Rides");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        ridesTextArea = new JTextArea();
        ridesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ridesTextArea);
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
            ridesTextArea.setText("No rides available.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Ride ride : rides) {
                sb.append("Customer: ").append(ride.getCustomer().getName()).append("\n");
                sb.append("Rider: ").append(ride.getRider().getName()).append("\n");
                sb.append("Start Time: ").append(ride.getStartTime().toString()).append("\n");
                sb.append("Source Location: ").append(ride.getSourceLocation()).append("\n");
                sb.append("Destination Location: ").append(ride.getDestinationLocation()).append("\n");
                sb.append("Distance: ").append(ride.getDistance()).append(" units").append("\n");
                sb.append("Fare: $").append(ride.getFare()).append("\n");
                sb.append("-----------------------").append("\n");
            }
            ridesTextArea.setText(sb.toString());
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
