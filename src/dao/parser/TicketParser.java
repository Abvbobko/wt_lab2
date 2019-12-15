package dao.parser;

import dao.DomParser;
import beans.Ticket;
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

public class TicketParser {
    private static final Logger logger = LogManager.getLogger();
    private List<Ticket> tickets;

    enum TicketTagName {
        FLIGHT_ID, USER_ID, PLACE, PRICE
    }

    public TicketParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        tickets = new ArrayList<>();
        logger.info("Tickets list created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList ticketsNodes = document.getDocumentElement().getElementsByTagName("Ticket");
            for (int i = 0; i < ticketsNodes.getLength(); i++) {
                if (ticketsNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    tickets.add(getTicketFromNode(ticketsNodes.item(i)));
                    logger.info("Ticket added to the list.");
                }
            }
            logger.info("All tickets were got by parser (" + tickets.size() + ")");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    private Ticket getTicketFromNode(Node ticketNode) {
        logger.info("New ticket created.");
        Ticket ticket = new Ticket();
        ticket.setId(Integer.parseInt(ticketNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList ticketProps = ticketNode.getChildNodes();
        TicketTagName ticketTagName;
        String str = null;
        for (int j = 0; j < ticketProps.getLength(); j++) {
            if ((ticketProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = ticketProps.item(j).getNodeName();
                    ticketTagName = TicketTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    String text = ticketProps.item(j).getTextContent();
                    switch (ticketTagName) {
                        case FLIGHT_ID:
                            ticket.setFlightID(Integer.parseInt(text));
                            break;
                        case USER_ID:
                            ticket.setUserID(Integer.parseInt(text));
                            break;
                        case PRICE:
                            ticket.setPrice(Integer.parseInt(text));
                            break;
                        case PLACE:
                            ticket.setPlace(Integer.parseInt(text));
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + "  was ignored.");
                }
            }
        }
        return ticket;
    }
}
