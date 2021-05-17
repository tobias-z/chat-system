package facades;

import domain.User;
import infrastructure.DBConnection;
import infrastructure.Database;
import infrastructure.database.DBUser;
import java.sql.SQLException;
import java.util.List;

public class UserFacade {

    private static UserFacade instance;
    private final Database<User> database;

    public UserFacade(Database<User> database) {
        this.database = database;
    }

    public static UserFacade getInstance() {
        if (instance == null) {
            Database<User> database = new DBUser(new DBConnection());
            instance = new UserFacade(database);
        }
        return instance;
    }

    public User createUser(User user) throws SQLException {
        return database.persist(user);
    }

    public User getUserById(int userId) throws SQLException {
        return database.getById(userId);
    }

    public List<User> getAllUsers() throws SQLException {
        return database.getAll();
    }

    public List<User> getAllUsersWithCustomQuery() throws SQLException {
        return database.createQuery("SELECT * FROM users");
    }

}
