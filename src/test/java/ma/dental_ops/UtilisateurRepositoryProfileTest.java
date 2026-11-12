package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/** JDBC integration test using an isolated in-memory database; no local MySQL is required. */
public class UtilisateurRepositoryProfileTest extends TestCase {
    private static final String URL = "jdbc:h2:mem:dental_profile_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE";

    @Override
    protected void setUp() throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, "sa", "");
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS utilisateur");
            statement.execute("CREATE TABLE utilisateur ("
                    + "id_utilisateur BIGINT PRIMARY KEY, nom_utilisateur VARCHAR(100) NOT NULL, "
                    + "prenom_utilisateur VARCHAR(100) NOT NULL, email VARCHAR(150) NOT NULL, "
                    + "telephone VARCHAR(20), mot_de_passe VARCHAR(255) NOT NULL, "
                    + "statut_utilisateur VARCHAR(30) NOT NULL)");
            statement.execute("INSERT INTO utilisateur VALUES "
                    + "(7, 'Avant', 'Test', 'login@example.com', '0600000000', 'hash', 'SECRETAIRE')");
        }
    }

    public void testProfileCoordinatesPersistAfterReloadWithoutChangingLoginOrRole() {
        UtilisateurRepositoryImplt repository = new UtilisateurRepositoryImplt();
        repository.updateProfile(7L, "Nouveau", "Nom", "0612345678");

        Utilisateur reloaded = repository.findUtilisateurById(7L);
        assertEquals("Nouveau", reloaded.getNomUtilisateur());
        assertEquals("Nom", reloaded.getPrenomUtilisateur());
        assertEquals("0612345678", reloaded.getTelephone());
        assertEquals("login@example.com", reloaded.getEmail());
        assertEquals("hash", reloaded.getMotDePasse());
        assertEquals(StatutUtilisateur.SECRETAIRE, reloaded.getStatutUtilisateur());
    }
}
