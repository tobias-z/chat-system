package intergration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;

class SetupIntegrationTests {

    static final String TEST_DB_URL = "jdbc:mysql://localhost:3306/startcode_test?serverTimezone=CET";

    static void runTestDatabaseMigration() {
        String USER = "dev";
        String PASS = "ax2";

        InputStream stream = SetupIntegrationTests.class.getClassLoader().getResourceAsStream("testinit.sql");
        if (stream == null) {
            System.out.println("Migration file, does not exist: ");
            throw new RuntimeException("testinit.sql");
        }
        try (Connection conn = DriverManager.getConnection(TEST_DB_URL, USER, PASS)) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Done running migration");
    }

}
