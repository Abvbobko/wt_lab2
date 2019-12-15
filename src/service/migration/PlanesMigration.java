package service.migration;

import beans.Plane;
import dao.DAO;
import exception.AlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlanesMigration {

    private static final Logger logger = LogManager.getLogger();
    private static final PlanesMigration instance = new PlanesMigration();

    private PlanesMigration() { }

    public static PlanesMigration getInstance() {
        return instance;
    }

    public void migrate(List<Plane> planes) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DAO dao = DAO.getInstance();
        Connection connection = dao.getDBConnection();
        for (Plane plane : planes) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM planes WHERE id = ?;");
                preparedStatement.setInt(1, plane.getId());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.first())
                    throw new AlreadyExistsException("Such plane already exists!");

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO planes (id, numberOfSeats) VALUES (?,?);");
                preparedStatement.setInt(1, plane.getId());
                preparedStatement.setInt(2, plane.getNumberOfSeats());
                preparedStatement.executeUpdate();
                logger.info("Plane was migrated to database.");
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