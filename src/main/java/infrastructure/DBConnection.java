package infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String DB_URL;

    private static final String USER = "dev";
    private static final String PASS = "ax2";

    public DBConnection(String url) {
        this.DB_URL = url == null ? "jdbc:mysql://localhost:3306/chat?serverTimezone=CET" : url;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public DBConnection() {
        this(null);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
