package dao.parser;

import dao.DomParser;
import beans.User;
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

public class UserParser {
    private static final Logger logger = LogManager.getLogger();
    private List<User> users;

    enum UserTagName {
        ADMIN, LOGIN, PASSWORD_HASH
    }

    public UserParser(String filepath) throws DataSourceException {
        File xmlFile = new File(filepath);
        users = new ArrayList<>();
        logger.info("Users list created.");
        try {
            Document document = DomParser.parseXmlFile(xmlFile);
            NodeList usersNodes = document.getDocumentElement().getElementsByTagName("user");
            for (int i = 0; i < usersNodes.getLength(); i++) {
                if (usersNodes.item(i).getNodeType() != Node.TEXT_NODE) {
                    users.add(getUserFromNode(usersNodes.item(i)));
                    logger.info("User added to the list.");
                }
            }
            logger.info("All users were got by parser (" + users.size() + ")");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new DataSourceException("File " + xmlFile.getName() + " not found or is incorrect.");
        }
    }

    public List<User> getUsers() {
        return users;
    }

    private User getUserFromNode(Node userNode) {
        logger.info("New user created.");
        User user = new User();
        user.setId(Integer.parseInt(userNode.getAttributes().getNamedItem("id").getNodeValue()));
        NodeList userProps = userNode.getChildNodes();
        UserTagName userTagName;
        String str = null;
        for (int j = 0; j < userProps.getLength(); j++) {
            if ((userProps.item(j).getNodeType() != Node.TEXT_NODE)) {
                try {
                    str = userProps.item(j).getNodeName();
                    userTagName = UserTagName.valueOf(str.toUpperCase().replace("-", "_"));
                    logger.info("Tag " + str + " was found.");
                    String text = userProps.item(j).getTextContent();
                    switch (userTagName) {
                        case ADMIN:
                            user.setAdmin(Boolean.parseBoolean(text));
                            break;
                        case LOGIN:
                            user.setLogin(text);
                            break;
                        case PASSWORD_HASH:
                            user.setPasswordHash(text);
                            break;
                    }
                } catch (Exception e) {
                    logger.warn("Tag " + str + "  was ignored.");
                }
            }
        }
        return user;
    }
}
