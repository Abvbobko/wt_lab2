package service;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import dao.DAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class Service {

    private static final Service service = new Service();
    private Service() {}
    public static Service getInstance(){
        return service;
    }

    private DAO dao = DAO.getInstance();
    private static final Logger logger = LogManager.getLogger();

    public void validateXml(){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(DAO.XSD_FILE_PATH));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(DAO.XML_FILE_PATH)));
        } catch (IOException | SAXException e) {
            logger.error(e.getMessage());
        }
    }

    public void connectDB() {
        dao.connectToDB();
    }

    public void closeDB() {
        dao.closeDBConnection();
    }

    public void parseXml() {
        dao.parseXml();
    }

    public void migrateToDB() {

    }
}
