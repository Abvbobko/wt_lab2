package beans;

import java.io.Serializable;

public class Ticket implements Serializable {

    private int flightID;
    private int userID;
    private int place;
    private int price;


    public void setPlace(int place) {
        this.place = place;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPlace() {
        return place;
    }

    public int getPrice() {
        return price;
    }

    public int getFlightID() {
        return flightID;
    }

    public Ticket(){ }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public Ticket(int flightID){
        this.flightID = flightID;
    }

}
