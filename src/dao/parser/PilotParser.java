package dao.parser;

import dao.DomParser;
import beans.Pilot;
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

public class PilotParser {
    private static final Logger logger = LogManager.getLogger();
    private List<Pilot> pilots;

    enum PilotTagName {
        NAME, SURNAME, MIDDLE_NAME, EXPERIENCE
    }

    public PilotParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        pilots = new ArrayList<>();
        logger.info("Pilots list created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList pilotsNodes = document.getDocumentElement().getElementsByTagName("pilot");
            for (int i = 0; i < pilotsNodes.getLength(); i++) {
                if (pilotsNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    pilots.add(getPilotFromNode(pilotsNodes.item(i)));
                    logger.info("Pilot added to the list.");
                }
            }
            logger.info("All pilots were got by parser (" + pilots.size() + ")");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Pilot> getPilots() {
        return pilots;
    }

    private Pilot getPilotFromNode(Node pilotNode) {
        logger.info("New pilot created.");
        Pilot pilot = new Pilot();
        pilot.setId(Integer.parseInt(pilotNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList pilotProps = pilotNode.getChildNodes();
        PilotTagName pilotTagName;
        String str = null;
        for (int j = 0; j < pilotProps.getLength(); j++) {
            if ((pilotProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = pilotProps.item(j).getNodeName();
                    pilotTagName = PilotTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    String text = pilotProps.item(j).getTextContent();
                    switch (pilotTagName) {
                        case NAME:
                            pilot.setName(text);
                            break;
                        case SURNAME:
                            pilot.setSurname(text);
                            break;
                        case MIDDLE_NAME:
                            pilot.setMiddleName(text);
                            break;
                        case EXPERIENCE:
                            pilot.setExperience(Integer.parseInt(text));
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + "  was ignored.");
                }
            }
        }
        return pilot;
    }
}
