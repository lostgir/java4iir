package ma.dental_ops.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private static DataSource instance;

    private final String url;
    private final String user;
    private final String pass;

    private DataSource() {
        Properties properties = new Properties();
        try (InputStream in = DataSource.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("application.properties introuvable dans le classpath");
            }
            properties.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de charger application.properties", e);
        }

        this.url = properties.getProperty("DataSource.url").trim();
        this.user = properties.getProperty("DataSource.user").trim();
        this.pass = properties.getProperty("DataSource.pass").trim();
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
            throw new RuntimeException("Impossible de se connecter a la base de donnees", e);
        }
    }
}
