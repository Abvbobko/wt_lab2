package beans;

import java.io.Serializable;

public class Ticket implements Serializable, Comparable {

    private Flight flight;
    //сделать привязку к flightID (flight Hash)

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }

    public Ticket(){ }

    public Ticket(Flight flight ){
        this.flight = flight;
    }

    @Override
    public String toString(){
        return "_TICKET_\n" + flight.toString();
    }

    @Override
    public int compareTo(Object o) {
        return flight.compareTo(((Ticket) o).getFlight());
    }
}
