package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.DataSource;
import ma.dental_ops.domain.DossierMedical;
import ma.dental_ops.repository.api.DossierMedicalRepository;

import java.sql.*;

public class DossierMedicalRepositoryImpl implements DossierMedicalRepository {

    @Override
    public DossierMedical save(DossierMedical dossier) {
        try (Connection connection =
                     DataSource.getInstance().getConnection()) {

            return save(dossier, connection);

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la création du dossier médical", e);
        }
    }

    @Override
    public DossierMedical save(
            DossierMedical dossier,
            Connection connection) {

        String sql = """
                INSERT INTO dossier_medical
                (patient_id, medecin_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, dossier.getPatientId());
            ps.setLong(2, dossier.getMedecinId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dossier.setId(rs.getLong(1));
                }
            }

            return dossier;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de l'insertion du dossier médical", e);
        }
    }

    @Override
    public DossierMedical findByPatientId(Long patientId) {

        String sql = """
                SELECT id_dossier, patient_id, medecin_id, date_creation
                FROM dossier_medical
                WHERE patient_id = ?
                """;

        try (Connection connection =
                     DataSource.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DossierMedical dossier = new DossierMedical();

                    dossier.setId(rs.getLong("id_dossier"));
                    dossier.setPatientId(rs.getLong("patient_id"));
                    dossier.setMedecinId(rs.getLong("medecin_id"));
                    dossier.setDateCreation(
                            rs.getTimestamp("date_creation").toLocalDateTime());

                    return dossier;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la recherche du dossier médical", e);
        }

        return null;
    }
}