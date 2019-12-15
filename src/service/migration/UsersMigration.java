package service.migration;

import beans.User;
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

public class UsersMigration {

    private static final Logger logger = LogManager.getLogger();
    private static final UsersMigration instance = new UsersMigration();

    private UsersMigration() { }

    public static UsersMigration getInstance() {
        return instance;
    }

    public void migrate(List<User> users) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DAO dao = DAO.getInstance();
        Connection connection = dao.getDBConnection();
        for (User user : users) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
                preparedStatement.setInt(1, user.getId());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.first())
                    throw new AlreadyExistsException("Such user already exists!");

                preparedStatement = connection.prepareStatement(
                        "INSERT INTO users (id, login, passwordHash, admin) VALUES (?,?,?,?);");
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getLogin());
                preparedStatement.setString(3, user.getPasswordHash());
                preparedStatement.setBoolean(4, user.isAdmin());
                preparedStatement.executeUpdate();
                logger.info("User was migrated to database.");
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