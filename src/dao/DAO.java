package dao;

import dao.*;

public class DAO {
    private static DAO dao = new DAO();

    private static final String XML_FILE_PATH = "resources\\airlineData.xml";

    private DB dataBase = DB.getInstance();
    private DomParser domParser = DomParser.getInstance();

    private DAO() {}
    public static DAO getInstance(){
        return dao;
    }

    public void connectToDB() {
        dataBase.connect();
    }

    public void parseXml() {
        XmlReader xmlReader = new XmlReader(XML_FILE_PATH);
    }
}
