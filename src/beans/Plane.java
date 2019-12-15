package beans;

import java.io.Serializable;
import java.time.LocalDate;

public class Plane implements Serializable, Comparable {
    private int numberOfSeats;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Plane() {}

    public Plane(int numberOfSeats){
        this.numberOfSeats = numberOfSeats;

    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public int compareTo(Object o) {
        Plane otherPlane = (Plane)o;
        if (numberOfSeats == otherPlane.getNumberOfSeats()) {
            return 0;
        }
        return (numberOfSeats > otherPlane.getNumberOfSeats()) ? 1 : -1;
    }
}
