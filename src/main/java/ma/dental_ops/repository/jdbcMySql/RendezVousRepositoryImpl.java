package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.DataSource;
import ma.dental_ops.repository.api.RendezVousRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RendezVousRepositoryImpl implements RendezVousRepository {

    @Override
    public long compterRendezVous(LocalDate dateDebut, LocalDate dateFin) {

        String sql = """
                SELECT COUNT(*)
                FROM rendez_vous
                WHERE date_rendez_vous BETWEEN ? AND ?
                AND statut <> 'ANNULE'
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
                    "Erreur lors du comptage des rendez-vous", e);
        }

        return 0;
    }

    @Override
    public long compterPatientsRecus(LocalDate dateDebut, LocalDate dateFin) {

        String sql = """
                SELECT COUNT(DISTINCT patient_id)
                FROM rendez_vous
                WHERE date_rendez_vous BETWEEN ? AND ?
                AND statut = 'TERMINE'
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
                    "Erreur lors du comptage des patients reçus", e);
        }

        return 0;
    }
    @Override
    public long compterRendezVousDuJour(LocalDate date) {

        String sql = """
            SELECT COUNT(*)
            FROM rendez_vous
            WHERE date_rendez_vous = ?
            AND statut <> 'ANNULE'
            """;

        try (Connection cn = DataSource.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors du comptage des rendez-vous du jour", e
            );
        }

        return 0;
    }
}