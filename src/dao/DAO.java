package dao;

import beans.*;
import dao.*;

import java.sql.Connection;
import java.util.List;

public class DAO {
    private static DAO dao = new DAO();

    public static final String XML_FILE_PATH = "resources\\airlineData.xml";
    public static final String XSD_FILE_PATH = "resources\\airlineData.xsd";

    private DB dataBase = DB.getInstance();
    private DomParser domParser = DomParser.getInstance();

    private DAO() {}
    public static DAO getInstance(){
        return dao;
    }

    public void connectToDB() {
        dataBase.connect();
    }

    public Connection getDBConnection(){
        return dataBase.getConnection();
    }

    public void closeDBConnection() {
        dataBase.closeConnection();
    }

    private XmlReader xmlReader = null;

    public void parseXml() {
        xmlReader = new XmlReader(XML_FILE_PATH);
    }

    public List<User> getUsers(){
        return xmlReader != null ? xmlReader.getUsers() : null;
    }

    public List<Flight> getFlights(){
        return xmlReader != null ? xmlReader.getFlights() : null;
    }

    public List<Pilot> getPilots(){
        return xmlReader != null ? xmlReader.getPilots() : null;
    }

    public List<Plane> getPlanes(){
        return xmlReader != null ? xmlReader.getPlanes() : null;
    }

    public List<Ticket> getTickets(){
        return xmlReader != null ? xmlReader.getTickets() : null;
    }

}
