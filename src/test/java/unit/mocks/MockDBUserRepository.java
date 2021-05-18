package unit.mocks;

import domain.user.User;
import infrastructure.DBRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MockDBUserRepository implements DBRepository<User, Integer> {

    private final List<User> users;

    public MockDBUserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public User persist(User entity) throws SQLException {
        users.add(entity);
        return entity;
    }

    @Override
    public User getByPrimaryKey(Integer integer) throws SQLException {
        return users.get(integer - 1);
    }

    @Override
    public List<User> getAll() throws SQLException {
        return users;
    }

    @Override
    public List<User> createQuery(String query) throws SQLException {
        throw new SQLException("Error :)");
    }
}
