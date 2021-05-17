package infrastructure.database;

import domain.User;
import infrastructure.DBConnection;
import infrastructure.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUser implements Database<User> {

    private final DBConnection connection;

    public DBUser(DBConnection connection) {
        this.connection = connection;
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("user.name"));
    }

    @Override
    public User persist(User entity) throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement("INSERT INTO users (name) VALUE (?)");
            statement.setString(1, entity.getName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return mapResultSetToUser(resultSet);
            else
                throw new SQLException("");
        } catch (SQLException throwables) {
            throw new SQLException("Unable to create user with name: " + entity.getName());
        }
    }

    @Override
    public User getById(int id) throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return mapResultSetToUser(resultSet);
            else
                throw new SQLException("Unable to find user");
        } catch (SQLException throwables) {
            throw new SQLException("Could not find user with id: " + id);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            return users;
        } catch (SQLException throwables) {
            throw new SQLException("Could not find any users");
        }
    }

    @Override
    public List<User> createQuery(String query) throws SQLException {
        try (Connection conn = connection.getConnection()) {
            var statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
            return users;
        } catch (SQLException throwables) {
            throw new SQLException("Did not get a result from query: " + query);
        }
    }

}
