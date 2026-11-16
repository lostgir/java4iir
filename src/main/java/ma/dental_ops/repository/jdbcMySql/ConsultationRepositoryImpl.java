package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.DataSource;
import ma.dental_ops.repository.api.ConsultationRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    @Override
    public long compterConsultations(LocalDate dateDebut, LocalDate dateFin) {

        String sql = """
                SELECT COUNT(*)
                FROM consultation
                WHERE date_consultation BETWEEN ? AND ?
                """;

        try (Connection cn = DataSource.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(dateDebut));
            ps.setDate(2, java.sql.Date.valueOf(dateFin));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors du comptage des consultations", e);
        }

        return 0;
    }
}