package infrastructure.database;

import domain.user.User;
import infrastructure.DBRepository;
import infrastructure.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUserRepository implements DBRepository<User, Integer> {

    private final DBConnection connection;

    public DBUserRepository(DBConnection connection) {
        this.connection = connection;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("users.name"));
    }

    private List<User> getListOfUsersFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapResultSetToUser(resultSet));
        }
        return users;
    }

    @Override
    public User persist(User entity) throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement(
                "INSERT INTO users (name) VALUE (?)", Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, entity.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            } else {
                throw new SQLException("");
            }
        } catch (SQLException throwables) {
            throw new SQLException(throwables.getMessage());
//            throw new SQLException("Unable to create user with name: " + entity.getName());
        }
    }

    @Override
    public User getByPrimaryKey(Integer primaryKey) throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM users WHERE id = ?;");
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUser(resultSet);
            } else {
                throw new SQLException("Unable to find user");
            }
        } catch (SQLException throwables) {
            throw new SQLException("Could not find user with id: " + primaryKey);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            return getListOfUsersFromResultSet(resultSet);
        } catch (SQLException throwables) {
            throw new SQLException("Could not find any users");
        }
    }

    @Override
    public List<User> createQuery(String query) throws SQLException {
        try {
            Connection conn = connection.getConnection();
            var statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            return getListOfUsersFromResultSet(resultSet);
        } catch (SQLException throwables) {
            throw new SQLException("Did not get a result from query: " + query);
        }
    }

}
