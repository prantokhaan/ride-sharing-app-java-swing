import java.io.Serializable;
import java.time.LocalDateTime;

public class Ride implements Serializable {
    private Customer customer;
    private Rider rider;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String sourceLocation;
    private String destinationLocation;
    private double distance;
    private double fare;

    public Ride(Customer customer, Rider rider, LocalDateTime startTime, String sourceLocation, String destinationLocation, double distance, double fare) {
        this.customer = customer;
        this.rider = rider;
        this.startTime = startTime;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.distance = distance;
        this.fare = fare;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Rider getRider() {
        return rider;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public double getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }
}
