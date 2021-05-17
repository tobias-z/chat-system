package facades;

import domain.User;
import infrastructure.DBConnection;
import infrastructure.DBRepository;
import infrastructure.database.DBUser;
import java.sql.SQLException;
import java.util.List;

public class UserFacade {

    private static UserFacade instance;
    private final DBRepository<User> db;

    public UserFacade(DBRepository<User> db) {
        this.db = db;
    }

    public static UserFacade getInstance() {
        if (instance == null) {
            DBRepository<User> DBRepository = new DBUser(new DBConnection());
            instance = new UserFacade(DBRepository);
        }
        return instance;
    }

    public User createUser(User user) throws SQLException {
        return db.persist(user);
    }

    public User getUserById(int userId) throws SQLException {
        return db.getById(userId);
    }

    public List<User> getAllUsers() throws SQLException {
        return db.getAll();
    }

    public List<User> getAllUsersWithCustomQuery() throws SQLException {
        return db.createQuery("SELECT * FROM users");
    }

}
