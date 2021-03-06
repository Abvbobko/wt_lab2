package service.migration;

import beans.Ticket;
import dao.DAO;
import exception.AlreadyExistsException;
import exception.DatabaseException;
//import service.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TicketsMigration {

    private static final Logger logger = LogManager.getLogger();
    private static final TicketsMigration instance = new TicketsMigration();

    private TicketsMigration() { }

    public static TicketsMigration getInstance() {
        return instance;
    }

    public void migrate(List<Ticket> tickets) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DAO dao = DAO.getInstance();
        Connection connection = dao.getDBConnection();
        for (Ticket ticket : tickets) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM tickets WHERE id = ?;");
                preparedStatement.setInt(1, ticket.getId());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.first())
                    throw new AlreadyExistsException("Such ticket already exists!");

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO tickets (id, price, flightID, User_ID, place) VALUES (?,?,?,?,?);");
                preparedStatement.setInt(1, ticket.getId());
                preparedStatement.setInt(2, ticket.getPrice());
                preparedStatement.setInt(3, ticket.getFlightID());
                preparedStatement.setInt(4, ticket.getUserID());
                preparedStatement.setInt(5, ticket.getPlace());
                preparedStatement.executeUpdate();
                logger.info("Ticket was migrated to database.");
            } catch (SQLException e) {
                logger.error("SQLException: ", e);
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