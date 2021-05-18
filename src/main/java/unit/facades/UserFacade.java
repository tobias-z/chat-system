package unit.facades;

import domain.user.User;
import infrastructure.DBConnection;
import infrastructure.DBRepository;
import infrastructure.database.DBUserRepository;
import java.sql.SQLException;
import java.util.List;

public class UserFacade {

    private static UserFacade instance;
    private final DBRepository<User, Integer> db;

    public UserFacade(DBRepository<User, Integer> db) {
        this.db = db;
    }

    public static UserFacade getInstance() {
        if (instance == null) {
            DBRepository<User, Integer> DBRepository = new DBUserRepository(new DBConnection());
            instance = new UserFacade(DBRepository);
        }
        return instance;
    }

    public User createUser(User user) throws SQLException {
        return db.persist(user);
    }

    public User getUserById(int userId) throws SQLException {
        return db.getByPrimaryKey(userId);
    }

    public List<User> getAllUsers() throws SQLException {
        return db.getAll();
    }

    public List<User> getAllUsersWithCustomQuery() throws SQLException {
        return db.createQuery("SELECT * FROM users");
    }

}
