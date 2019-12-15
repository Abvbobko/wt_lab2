package dao;

import dao.parser.*;
import beans.*;
import exception.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;
import java.util.List;

public class XmlReader {
    private static final Logger logger = LogManager.getLogger();
    private UserParser userParser;
    private FlightParser flightParser;
    private PilotParser pilotParser;
    private PlaneParser planeParser;
    private TicketParser ticketParser;

    private List<User> users;
    private List<Flight> flights;
    private List<Plane> planes;
    private List<Pilot> pilots;
    private List<Ticket> tickets;

    XmlReader(String file) {
        logger.info("Xml reader starts parsing file - " + file + ".");
        try {
            userParser = new UserParser(file);
            //System.out.println(userParser.getUsers());
            flightParser = new FlightParser(file);
            pilotParser = new PilotParser(file);
            planeParser = new PlaneParser(file);
            ticketParser = new TicketParser(file);
        } catch (DataSourceException e) {
            logger.error(e.getMessage());
        }
    }

    public List<User> getUsers() {
        if (users == null) {
            users = userParser.getUsers();
            logger.info("User list is ready.");
        }
        return users;
    }

    public List<Flight> getFlights() {
        if (flights == null) {
            flights = flightParser.getFlights();
            logger.info("Flight list is ready.");
        }
        return flights;
    }

    public List<Pilot> getPilots() {
        if (pilots == null) {
            pilots = pilotParser.getPilots();
            logger.info("Pilot list is ready.");
        }
        return pilots;
    }

    public List<Plane> getPlanes() {
        if (planes == null) {
            planes = planeParser.getPlanes();
            logger.info("Plane list is ready.");
        }
        return planes;
    }

    public List<Ticket> getTickets() {
        if (tickets == null) {
            tickets = ticketParser.getTickets();
            logger.info("Ticket list is ready.");
        }
        return tickets;
    }
}
