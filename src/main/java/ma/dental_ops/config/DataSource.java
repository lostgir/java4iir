
package ma.dental_ops.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSource {

    private static DataSource instance;

    private String url;
    private String user;
    private String pass;

    private DataSource() {
        Properties properties = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Fichier application.properties introuvable");
            }

            properties.load(input);

            url = properties.getProperty("DataSource.url");
            user = properties.getProperty("DataSource.user");
            pass = properties.getProperty("DataSource.pass");

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erreur de lecture de la configuration", e);
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
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Impossible de se connecter à MySQL", e);
        }
    }
}
