package se.lexicon.eventmanagement.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseConfigTest {

    @TempDir
    Path tempDirectory;

    @Test
    void loadsConfigurationFromLocalEnvFile() throws IOException {
        Path localConfig = tempDirectory.resolve(".env");
        Files.writeString(localConfig, """
                EVENT_DB_URL=jdbc:mysql://localhost:3307/event_management
                EVENT_DB_USER=event_app
                EVENT_DB_PASSWORD=local_test_password
                """);

        DatabaseConfig config = DatabaseConfig.load(localConfig);

        assertEquals("jdbc:mysql://localhost:3307/event_management", config.url());
        assertEquals("event_app", config.username());
        assertEquals("local_test_password", config.password());
    }

    @Test
    void rejectsBlankRequiredValues() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DatabaseConfig(" ", "event_app", "password")
        );
    }
}
