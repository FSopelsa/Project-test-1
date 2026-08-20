package se.lexicon.eventmanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Performs the smallest useful JDBC check before the application schema exists.
 */
public final class DatabaseConnectionCheck {

    private DatabaseConnectionCheck() {
    }

    /**
     * Connects to the configured database and executes {@code SELECT 1}.
     *
     * @param config local JDBC configuration
     * @return the integer returned by the database
     * @throws SQLException when a connection or query fails
     */
    public static int executeSelectOne(DatabaseConfig config) throws SQLException {
        Objects.requireNonNull(config, "config must not be null");

        try (Connection connection = DriverManager.getConnection(
                config.url(), config.username(), config.password());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {

            if (!resultSet.next()) {
                throw new SQLException("SELECT 1 returned no result");
            }
            return resultSet.getInt(1);
        }
    }
}
