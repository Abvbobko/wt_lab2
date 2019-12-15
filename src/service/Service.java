package service;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import dao.DAO;
import org.xml.sax.SAXException;

public class Service {

    private static final Service service = new Service();
    private Service() {}
    public static Service getInstance(){
        return service;
    }

    private DAO dao = DAO.getInstance();

    public Boolean validate(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }

    public void connectDB() {
        dao.connectToDB();
        //DB dao = new DB();
        ;
    }

}
