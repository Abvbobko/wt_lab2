package dao.parser;

import dao.DomParser;
import beans.Plane;
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

public class PlaneParser {
    private static final Logger logger = LogManager.getLogger();
    private List<Plane> planes;

    enum PlaneTagName {
        NUM_OF_SEATS
    }

    public PlaneParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        planes = new ArrayList<>();
        logger.info("Planes list created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList planesNodes = document.getDocumentElement().getElementsByTagName("Plane");
            for (int i = 0; i < planesNodes.getLength(); i++) {
                if (planesNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    planes.add(getPlaneFromNode(planesNodes.item(i)));
                    logger.info("Plane added to the list.");
                }
            }
            logger.info("All planes were got by parser (" + planes.size() + ")");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<Plane> getPlanes() {
        return planes;
    }

    private Plane getPlaneFromNode(Node planeNode) {
        logger.info("New plane created.");
        Plane plane = new Plane();
        plane.setId(Integer.parseInt(planeNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList planeProps = planeNode.getChildNodes();
        PlaneTagName planeTagName;
        String str = null;
        for (int j = 0; j < planeProps.getLength(); j++) {
            if ((planeProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = planeProps.item(j).getNodeName();
                    planeTagName = PlaneTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    String text = planeProps.item(j).getTextContent();
                    switch (planeTagName) {
                        case NUM_OF_SEATS:
                            plane.setNumberOfSeats(Integer.parseInt(text));
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + "  was ignored.");
                }
            }
        }
        return plane;
    }
}
