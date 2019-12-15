package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DB {

    private static final DB db = new DB();
    private DB() {}
    static DB getInstance(){
        return db;
    }

    private static final Logger logger = LogManager.getLogger();

    private Connection connection = null;

    public Connection getConnection(){
        return connection;
    }

    void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String URL = "jdbc:mysql://127.0.0.1:3306/mydb"
                    + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String LOGIN = "root";
            String PASSWORD = "iam10903992";

            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            logger.info("Connection to database was successfully.");
        } catch (ClassNotFoundException | SQLException e) {

            logger.error(e.getMessage());
        }
    }

    void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                logger.error(e.getMessage());
            }
        }
    }


}



