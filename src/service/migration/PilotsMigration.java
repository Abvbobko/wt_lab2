package service.migration;

import beans.Pilot;
import dao.DAO;
import exception.AlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PilotsMigration {

    private static final Logger logger = LogManager.getLogger();
    private static final PilotsMigration instance = new PilotsMigration();

    private PilotsMigration() { }

    public static PilotsMigration getInstance() {
        return instance;
    }

    public void migrate(List<Pilot> pilots) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DAO dao = DAO.getInstance();
        Connection connection = dao.getDBConnection();
        for (Pilot pilot : pilots) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM pilots WHERE id = ?;");
                preparedStatement.setInt(1, pilot.getId());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.first())
                    throw new AlreadyExistsException("Such pilot already exists!");

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO pilots (id, experience, name, surname, middleName) VALUES (?,?,?,?,?);");
                preparedStatement.setInt(1, pilot.getId());
                preparedStatement.setInt(2, pilot.getExperience());
                preparedStatement.setString(3, pilot.getName());
                preparedStatement.setString(4, pilot.getSurname());
                preparedStatement.setString(5, pilot.getMiddleName());
                preparedStatement.executeUpdate();
                logger.info("Pilot was migrated to database.");
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