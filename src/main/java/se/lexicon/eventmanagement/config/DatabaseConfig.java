package se.lexicon.eventmanagement.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Contains the JDBC settings needed by the application.
 *
 * <p>Configuration is resolved in this order: JVM system properties,
 * environment variables, and the ignored local {@code .env} file.</p>
 */
public record DatabaseConfig(String url, String username, String password) {

    private static final Path DEFAULT_LOCAL_CONFIG = Path.of(".env");

    public DatabaseConfig {
        requireText(url, "Database URL");
        requireText(username, "Database username");
        requireText(password, "Database password");
    }

    /**
     * Loads database configuration for a normal application run.
     *
     * @return validated JDBC configuration
     */
    public static DatabaseConfig load() {
        return load(DEFAULT_LOCAL_CONFIG);
    }

    static DatabaseConfig load(Path localConfigPath) {
        Properties localConfig = loadLocalConfig(localConfigPath);

        return new DatabaseConfig(
                resolve("event.db.url", "EVENT_DB_URL", localConfig),
                resolve("event.db.user", "EVENT_DB_USER", localConfig),
                resolve("event.db.password", "EVENT_DB_PASSWORD", localConfig)
        );
    }

    private static Properties loadLocalConfig(Path localConfigPath) {
        Properties properties = new Properties();
        if (!Files.isRegularFile(localConfigPath)) {
            return properties;
        }

        try (Reader reader = Files.newBufferedReader(localConfigPath, StandardCharsets.UTF_8)) {
            properties.load(reader);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not read local database configuration from " + localConfigPath,
                    exception
            );
        }
    }

    private static String resolve(String systemProperty, String environmentVariable,
                                  Properties localConfig) {
        String value = System.getProperty(systemProperty);
        if (isBlank(value)) {
            value = System.getenv(environmentVariable);
        }
        if (isBlank(value)) {
            value = localConfig.getProperty(environmentVariable);
        }
        if (isBlank(value)) {
            throw new IllegalStateException(
                    "Missing database setting " + environmentVariable
                            + ". Copy .env.example to .env and replace its placeholders."
            );
        }
        return value.trim();
    }

    private static void requireText(String value, String label) {
        if (isBlank(value)) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
