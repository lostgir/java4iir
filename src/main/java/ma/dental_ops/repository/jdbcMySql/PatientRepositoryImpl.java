package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.DataSource;
import ma.dental_ops.domain.Patient;
import ma.dental_ops.repository.api.PatientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    @Override
    public Patient save(Patient patient) {
        try (Connection connection =
                     DataSource.getInstance().getConnection()) {

            return save(patient, connection);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du patient", e);
        }
    }

    @Override
    public Patient save(Patient patient, Connection connection) {

        String sql = """
            INSERT INTO patient (nom, prenom, telephone, email)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getTelephone());
            ps.setString(4, patient.getEmail());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    patient.setId(rs.getLong(1));
                }
            }

            return patient;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion du patient", e);
        }
    }

    @Override
    public Patient findById(Long id) {

        String sql = "SELECT * FROM patient WHERE id_patient = ?";

        try (Connection connection =
                     DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapPatient(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Patient> findAll() {

        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (Connection connection =
                     DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patients;
    }

    @Override
    public void updateArchiveStatus(Long id, boolean archive) {

        String sql = "UPDATE patient SET archive = ? WHERE id_patient = ?";

        try (Connection connection =
                     DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBoolean(1, archive);
            ps.setLong(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> searchByNomOrTelephone(String searchTerm) {

        List<Patient> patients = new ArrayList<>();

        String sql = """
            SELECT * FROM patient
            WHERE nom LIKE ? OR telephone LIKE ?
        """;

        try (Connection connection =
                     DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String recherche = "%" + searchTerm + "%";

            ps.setString(1, recherche);
            ps.setString(2, recherche);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(mapPatient(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patients;
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {

        Patient patient = new Patient();

        patient.setId(rs.getLong("id_patient"));
        patient.setNom(rs.getString("nom"));
        patient.setPrenom(rs.getString("prenom"));
        patient.setTelephone(rs.getString("telephone"));
        patient.setEmail(rs.getString("email"));
        patient.setArchive(rs.getBoolean("archive"));

        return patient;
    }
}