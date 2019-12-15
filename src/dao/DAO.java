package dao;

import dao.*;

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

    public void closeDBConnection() {
        dataBase.closeConnection();
    }

    public void parseXml() {
        XmlReader xmlReader = new XmlReader(XML_FILE_PATH);
    }
}
