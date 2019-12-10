package beans;

import java.io.Serializable;
import java.time.LocalDate;

public class Plane implements Serializable, Comparable {
    private Pilot pilot;
    private final String brand;
    private final int numberOfSeats;
    private final LocalDate yearOfManufacture;

    Plane(String brand, int numberOfSeats, LocalDate yearOfManufacture){
        this.brand = brand;
        this.numberOfSeats = numberOfSeats;
        this.yearOfManufacture = yearOfManufacture;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public LocalDate getYearOfManufacture(){

        return this.yearOfManufacture;
    }

    public String getBrand(){

        return this.brand;
    }

    public Pilot getPilot(){

        return this.pilot;
    }

    public void setPilot(Pilot pilot){

        this.pilot = pilot;
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
