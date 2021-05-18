package intergration;

import static org.junit.jupiter.api.Assertions.fail;

import facades.UserFacade;
import infrastructure.DBConnection;
import infrastructure.database.DBUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBUserTest extends SetupIntegrationTests {

    private static UserFacade FACADE;

    @BeforeEach
    void setUp() {
        runTestDatabaseMigration();
        FACADE = new UserFacade(new DBUserRepository(new DBConnection(TEST_DB_URL)));
    }

    @Test
    @DisplayName("user should be created")
    void userShouldBeCreated() {
        fail("FAILED :)");
    }

}
