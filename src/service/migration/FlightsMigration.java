package service.migration;

import beans.Flight;
import dao.DAO;
import exception.AlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FlightsMigration {

    private static final Logger logger = LogManager.getLogger();
    private static final FlightsMigration instance = new FlightsMigration();

    private FlightsMigration() { }

    public static FlightsMigration getInstance() {
        return instance;
    }

    public void migrate(List<Flight> flights) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DAO dao = DAO.getInstance();
        Connection connection = dao.getDBConnection();
        for (Flight flight : flights) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM flights WHERE id = ?;");
                preparedStatement.setInt(1, flight.getId());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.first())
                    throw new AlreadyExistsException("Such flight already exists!");

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO flights (id, departureTime, arrivalTime, dateOfFlight, " +
                                "fromCity, toCity, Planes_ID) VALUES (?,?,?,?,?,?,?);");
                preparedStatement.setInt(1, flight.getId());
                preparedStatement.setString(2, flight.getDepartureTime());
                preparedStatement.setString(3, flight.getArrivalTime());
                preparedStatement.setString(4, flight.getDateOfFlight());
                preparedStatement.setString(5, flight.getFromCity());
                preparedStatement.setString(5, flight.getToCity());
                preparedStatement.setInt(6, flight.getPlaneID());
                preparedStatement.executeUpdate();
                logger.info("Flight was migrated to database.");
            } catch (SQLException e) {
                logger.error("SQLException: ", e);
                System.out.println(e.getMessage());
            } catch (AlreadyExistsException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (preparedStatement != null)
                        preparedStatement.close();
                } catch (SQLException e) {
                    logger.error("SQLException during closing resultSet or preparedStatement.");
                }
            }
        }
    }
}