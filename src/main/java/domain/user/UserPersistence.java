package domain.user;

import domain.persistence.Persistence;
import infrastructure.DBRepository;
import java.sql.SQLException;

public class UserPersistence implements Persistence<User> {

    private final User user;
    private final DBRepository<User, Integer> db;

    public UserPersistence(User user, DBRepository<User, Integer> db) {
        this.user = user;
        this.db = db;
    }

    @Override
    public boolean isValid() {
        if (user.getName().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public User commit() throws SQLException {
        return db.persist(user);
    }
}
