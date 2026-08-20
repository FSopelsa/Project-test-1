package se.lexicon.eventmanagement.config;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("database")
class DatabaseConnectionSmokeTest {

    @Test
    void databaseRespondsToSelectOne() throws SQLException {
        int result = DatabaseConnectionCheck.executeSelectOne(DatabaseConfig.load());

        assertEquals(1, result);
    }
}
