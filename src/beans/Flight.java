package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Flight implements Serializable, Comparable {
    private LocalDate dateOfFlight;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String fromCity;
    private String toCity;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDateOfFlight(String dateOfFlight) {
        this.dateOfFlight = LocalDate.parse(dateOfFlight);
    }

    public String getDateOfFlight() {
        return (dateOfFlight == null) ? null : dateOfFlight.toString();
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = LocalTime.parse(departureTime);
    }

    public String getDepartureTime() {
        return (departureTime == null) ? null : departureTime.toString();
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = LocalTime.parse(arrivalTime);
    }

    public String getArrivalTime() {
        return (arrivalTime == null) ? null : arrivalTime.toString();
    }


    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Flight() { }

    public Flight(String fromCity, String toCity, LocalDate dateOfFlight, LocalTime departureTime,
                  LocalTime arrivalTime) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.dateOfFlight = dateOfFlight;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Object o) {
        Flight otherFlight = (Flight)o;

        int compareResult = dateOfFlight.compareTo(LocalDate.parse(otherFlight.getDateOfFlight()));
        if (compareResult == 0) {
            compareResult = arrivalTime.compareTo(LocalTime.parse(otherFlight.getArrivalTime()));
            if (compareResult == 0) {
                compareResult = fromCity.compareTo(otherFlight.getFromCity());
                if (compareResult == 0) {
                    compareResult = toCity.compareTo(otherFlight.getToCity());
                }
            }
        }
        return compareResult;
    }

}

