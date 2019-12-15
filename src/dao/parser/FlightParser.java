package dao.parser;

import dao.DomParser;
import beans.Flight;
import exception.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightParser {
    private static final Logger logger = LogManager.getLogger();
    private List<Flight> flights;

    enum FlightTagName {
        DATE_OF_FLIGHT, DEPARTURE_TIME, ARRIVAL_TIME, FROM_CITY, TO_CITY
    }

    public FlightParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        flights = new ArrayList<>();
        logger.info("Flights list created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList flightsNodes = document.getDocumentElement().getElementsByTagName("Flight");
            for (int i = 0; i < flightsNodes.getLength(); i++) {
                if (flightsNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    flights.add(getFlightFromNode(flightsNodes.item(i)));
                    logger.info("Flight added to the list.");
                }
            }
            logger.info("All flights were got by parser (" + flights.size() + ")");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Flight> getFlights() {
        return flights;
    }

    private Flight getFlightFromNode(Node flightNode) {
        logger.info("New flight created.");
        Flight flight = new Flight();
        flight.setId(Integer.parseInt(flightNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList flightProps = flightNode.getChildNodes();
        FlightTagName flightTagName;
        String str = null;
        for (int j = 0; j < flightProps.getLength(); j++) {
            if ((flightProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = flightProps.item(j).getNodeName();
                    flightTagName = FlightTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    String text = flightProps.item(j).getTextContent();
                    switch (flightTagName) {
                        case DATE_OF_FLIGHT:
                            flight.setDateOfFlight(text);
                            break;
                        case DEPARTURE_TIME:
                            flight.setDepartureTime(text);
                            break;
                        case ARRIVAL_TIME:
                            flight.setArrivalTime(text);
                            break;
                        case FROM_CITY:
                            flight.setFromCity(text);
                            break;
                        case TO_CITY:
                            flight.setToCity(text);
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + "  was ignored.");
                }
            }
        }
        return flight;
    }
}
