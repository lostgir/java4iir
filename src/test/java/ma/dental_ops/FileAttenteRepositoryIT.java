package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.repository.jdbcMySql.FileAttenteRepositoryImpl;

import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;

public class FileAttenteRepositoryIT extends TestCase {
    private static final String URL = "jdbc:h2:mem:dental_profile_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE";
    private static final LocalDate JOUR = LocalDate.of(2026, 11, 12);

    @Override
    protected void setUp() throws Exception {
        try (var connection = DriverManager.getConnection(URL, "sa", "");
             var sql = connection.createStatement()) {
            sql.execute("DROP TABLE IF EXISTS rendez_vous");
            sql.execute("DROP TABLE IF EXISTS patient");
            sql.execute("CREATE TABLE patient (id_patient BIGINT PRIMARY KEY, nom VARCHAR(100), prenom VARCHAR(100))");
            sql.execute("INSERT INTO patient VALUES (1, 'Test', 'Patient')");
            sql.execute("CREATE TABLE rendez_vous (id_rendez_vous BIGINT PRIMARY KEY, patient_id BIGINT, "
                    + "date_rendez_vous DATE, heure_rendez_vous TIME, statut VARCHAR(20), heure_arrivee TIMESTAMP)");
            sql.execute("INSERT INTO rendez_vous VALUES "
                    + "(1, 1, DATE '2026-11-12', TIME '09:00:00', 'ARRIVE', TIMESTAMP '2026-11-12 09:15:00'), "
                    + "(2, 1, DATE '2026-11-12', TIME '09:30:00', 'ARRIVE', TIMESTAMP '2026-11-12 09:10:00'), "
                    + "(3, 1, DATE '2026-11-12', TIME '10:00:00', 'CONFIRME', NULL)");
        }
    }

    public void testOrdreArriveesEtSortieDeFile() throws Exception {
        var repository = new FileAttenteRepositoryImpl();
        assertEquals(List.of(2L, 1L), repository.findArriveesDuJour(JOUR).stream()
                .map(PatientEnAttente::rendezVousId).toList());
        repository.passerEnCours(2, JOUR);
        assertEquals(List.of(1L), repository.findArriveesDuJour(JOUR).stream()
                .map(PatientEnAttente::rendezVousId).toList());

        repository.marquerAbsent(3, JOUR);
        assertTrue(repository.findRendezVousDuJour(JOUR).isEmpty());
        try (var connection = DriverManager.getConnection(URL, "sa", "");
             var sql = connection.createStatement();
             var rows = sql.executeQuery("SELECT statut, heure_arrivee FROM rendez_vous WHERE id_rendez_vous = 3")) {
            assertTrue(rows.next());
            assertEquals("ABSENT", rows.getString("statut"));
            assertNull(rows.getTimestamp("heure_arrivee"));
        }
    }
}
