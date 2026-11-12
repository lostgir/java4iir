package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.domain.Patient;
import ma.dental_ops.repository.jdbcMySql.PatientRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/** JDBC integration coverage for stable filtered patient paging on H2/MySQL mode. */
public class PatientRepositorySearchPageTest extends TestCase {
    private static final String URL = "jdbc:h2:mem:dental_profile_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE";

    @Override
    protected void setUp() throws Exception {
        try (Connection connection = DriverManager.getConnection(URL, "sa", "");
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS dossier_medical");
            statement.execute("DROP TABLE IF EXISTS patient");
            statement.execute("CREATE TABLE patient (id_patient BIGINT PRIMARY KEY, nom VARCHAR(100) NOT NULL, "
                    + "prenom VARCHAR(100), telephone VARCHAR(20), email VARCHAR(150), archive BOOLEAN DEFAULT FALSE)");
            statement.execute("CREATE TABLE dossier_medical (id_dossier BIGINT PRIMARY KEY, patient_id BIGINT NOT NULL)");
            try (PreparedStatement patient = connection.prepareStatement(
                    "INSERT INTO patient VALUES (?, ?, ?, ?, ?, FALSE)");
                 PreparedStatement dossier = connection.prepareStatement(
                    "INSERT INTO dossier_medical VALUES (?, ?)")) {
                for (int id = 1; id <= 1000; id++) {
                    patient.setInt(1, id);
                    patient.setString(2, String.format("Patient%04d", id));
                    patient.setString(3, "Test");
                    patient.setString(4, String.format("0600%04d", id));
                    patient.setString(5, "patient" + id + "@example.test");
                    patient.addBatch();
                    dossier.setInt(1, id);
                    dossier.setInt(2, id);
                    dossier.addBatch();
                }
                patient.executeBatch();
                dossier.executeBatch();
            }
        }
    }

    public void testPagesAreStableAndCountMatchesFilterIncludingDossierNumber() {
        PatientRepositoryImpl repository = new PatientRepositoryImpl();
        PageResult<Patient> first = repository.searchPage("", 0, 100);
        PageResult<Patient> second = repository.searchPage("", 1, 100);
        assertEquals(1000L, first.totalItems());
        assertEquals(10, first.totalPages());
        Set<Long> firstIds = new HashSet<>();
        for (Patient patient : first.items()) firstIds.add(patient.getId());
        for (Patient patient : second.items()) assertFalse(firstIds.contains(patient.getId()));

        PageResult<Patient> dossierMatch = repository.searchPage("555", 0, 25);
        assertEquals(1L, dossierMatch.totalItems());
        assertEquals(Long.valueOf(555), dossierMatch.items().get(0).getId());
    }
}

