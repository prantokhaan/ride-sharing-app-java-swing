import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.List;

public class BookRidePage extends JFrame {
    private JLabel sourceLabel;
    private JTextField sourceField;
    private JLabel destinationLabel;
    private JTextField destinationField;
    private JButton bookButton;

    private Customer customer;
    private List<Rider> riders;

    public BookRidePage(Customer customer, List<Rider> riders) {
        this.customer = customer;
        this.riders = riders;

        setTitle("Book a Ride");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));

        sourceLabel = new JLabel("Source Location:");
        inputPanel.add(sourceLabel);

        sourceField = new JTextField();
        inputPanel.add(sourceField);

        destinationLabel = new JLabel("Destination Location:");
        inputPanel.add(destinationLabel);

        destinationField = new JTextField();
        inputPanel.add(destinationField);

        add(inputPanel, BorderLayout.CENTER);

        bookButton = new JButton("Book");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookRide();
            }
        });
        add(bookButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void bookRide() {
    String sourceLocation = sourceField.getText();
    String destinationLocation = destinationField.getText();

    // Perform validation on the source and destination locations
    if (sourceLocation.isEmpty() || destinationLocation.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both source and destination locations.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Select a rider for the ride
    Rider selectedRider = selectRider();

    if (selectedRider == null) {
        JOptionPane.showMessageDialog(this, "Sorry, no available riders at the moment.", "Rider Unavailable", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Perform other operations related to booking the ride
    LocalDateTime startTime = LocalDateTime.now();
    double distance = calculateDistance(sourceLocation, destinationLocation);
    double fare = calculateFare(distance);

    Ride ride = new Ride(customer, selectedRider, startTime, sourceLocation, destinationLocation, distance, fare);

    saveBookRide(ride, "ride.ser");

    // Save the ride details or perform any other necessary actions

    // Show the ride details in a dialog
    String rideDetails = "Ride Details:\n\n" +
            "Customer: " + ride.getCustomer().getUsername() + "\n" +
            "Rider: " + ride.getRider().getName() + "\n" +
            "Start Time: " + ride.getStartTime().toString() + "\n" +
            "Source Location: " + ride.getSourceLocation() + "\n" +
            "Destination Location: " + ride.getDestinationLocation() + "\n" +
            "Distance: " + ride.getDistance() + " units\n" +
            "Fare: $" + ride.getFare();

    JOptionPane.showMessageDialog(this, rideDetails, "Ride Details", JOptionPane.INFORMATION_MESSAGE);
    dispose();
}

    private Rider selectRider() {
        // Implement logic to select a rider for the ride 
        //  use the 'riders' list to choose from available riders
        // Return the selected rider

        
        if (!riders.isEmpty()) {
            int randomIndex = (int) (Math.random() * riders.size());
            return riders.get(randomIndex);
        } else {
            return null;
        }
    }

    private double calculateDistance(String sourceLocation, String destinationLocation) {
        // Implement logic to calculate the distance between the source and destination locations
        // Return the calculated distance

        // Temporarily, this implementation assumes a fixed distance of 10 units
        return 10.0;
    }

    private double calculateFare(double distance) {
        // Implement logic to calculate the fare based on the distance
        // Return the calculated fare

        // Temporarily, this implementation assumes a fixed fare rate of $2 per unit distance
        return 2.0 * distance;
    }

    public static void saveBookRide(Ride ride, String filename){
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(ride);
            objectOut.close();
            fileOut.close();
            System.out.println("Ride object saved to " + filename);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
