package ma.dental_ops.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fabrique JDBC unique de l'application.
 *
 * <p>La fabrique est un singleton, mais chaque appel a
 * {@link #createConnection()} retourne une nouvelle connexion que l'appelant
 * doit fermer.</p>
 */
public final class JdbcConnectionFactory {

    private static final JdbcConnectionFactory INSTANCE = new JdbcConnectionFactory();

    private final String url;
    private final String user;
    private final String password;

    private JdbcConnectionFactory() {
        // Cette partie permet de lire de lire depuis app propertues
        Properties properties = loadProperties();
        // Puis récuperer le driver , url , user , et password
        String driver = requiredProperty(properties, "DataSource.driver");
        url = requiredProperty(properties, "DataSource.url");
        user = requiredProperty(properties, "DataSource.user");
        password = propertyAllowingEmptyValue(properties, "DataSource.pass");

        // try catch pour verifier l'existance du driver jdbc
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Pilote JDBC introuvable : " + driver, e);
        }
    }

    public static JdbcConnectionFactory getInstance() {
        return INSTANCE;
    }
    // Contact avec my sql pour la connection
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Charge les propriétés JDBC depuis le classpath.
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = JdbcConnectionFactory.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException(
                        "application.properties introuvable. Copiez application.properties.example avant de demarrer.");
            }
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de lire application.properties", e);
        }
    }

    private static String requiredProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Propriete obligatoire absente : " + key);
        }
        return value.trim();
    }

    private static String propertyAllowingEmptyValue(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Propriete obligatoire absente : " + key);
        }
        return value.trim();
    }
}
