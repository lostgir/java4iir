
package ma.dental_ops.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSource {

    private static DataSource instance;

    private final String url;
    private final String user;
    private final String password;

    private DataSource() {

        Properties properties = new Properties();

        try (InputStream input =
                     getClass().getClassLoader()
                             .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Fichier application.properties introuvable"
                );
            }

            properties.load(input);

            url = properties.getProperty("DataSource.url");
            user = properties.getProperty("DataSource.user");
            password = properties.getProperty("DataSource.pass");

            if (url == null || user == null || password == null) {
                throw new RuntimeException(
                        "Configuration MySQL incomplète"
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erreur de lecture de la configuration MySQL", e
            );
        }
    }

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Impossible de se connecter à MySQL", e
            );
        }
    }
}
