package unit.facades;

import static org.junit.jupiter.api.Assertions.*;

import domain.user.User;
import facades.UserFacade;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import unit.mocks.MockDBUserRepository;

class UserFacadeTest {

    private static final UserFacade FACADE = new UserFacade(new MockDBUserRepository());

    @Test
    @DisplayName("get all should return a list of users")
    void getAllShouldReturnAListOfUsers() throws SQLException {
        List<User> users = FACADE.getAllUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("get by id should return a user")
    void getByIdShouldReturnAUser() throws SQLException {
        User user = FACADE.getUserById(1);
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                //TODO (tz): implement this!
                throw new UnsupportedOperationException("Not yet implemented!");
            }
        });
        assertNotNull(user);
    }

    @Test
    void createUser() throws SQLException {
        String userName = "Bob";
        User user = FACADE.createUser(new User(userName));
        assertEquals(userName, user.getName());
    }

}