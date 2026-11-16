package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.DataSource;
import ma.dental_ops.dto.PatientEnAttente;
import ma.dental_ops.dto.RendezVousDuJour;
import ma.dental_ops.repository.api.FileAttenteRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileAttenteRepositoryImpl implements FileAttenteRepository {
    @Override
    public void enregistrerArrivee(long rendezVousId, LocalDate jour) {
        String sql = "UPDATE rendez_vous SET statut = 'ARRIVE', heure_arrivee = CURRENT_TIMESTAMP "
                + "WHERE id_rendez_vous = ? AND date_rendez_vous = ? AND statut IN ('PLANIFIE','CONFIRME')";
        updateStatus(sql, rendezVousId, jour, "Rendez-vous introuvable, déjà arrivé, ou non prévu pour cette date");
    }

    @Override
    public void passerEnCours(long rendezVousId, LocalDate jour) {
        String sql = "UPDATE rendez_vous SET statut = 'EN_COURS' "
                + "WHERE id_rendez_vous = ? AND date_rendez_vous = ? AND statut = 'ARRIVE'";
        updateStatus(sql, rendezVousId, jour, "Seul un patient arrivé aujourd'hui peut passer en cours");
    }

    private void updateStatus(String sql, long rendezVousId, LocalDate jour, String invalidMessage) {
        try (Connection cn = DataSource.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, rendezVousId);
            ps.setDate(2, Date.valueOf(jour));
            if (ps.executeUpdate() != 1) throw new IllegalStateException(invalidMessage);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de mise à jour de la file d'attente", e);
        }
    }

    @Override
    public List<PatientEnAttente> findArriveesDuJour(LocalDate jour) {
        String sql = "SELECT r.id_rendez_vous, p.id_patient, p.nom, p.prenom, r.heure_arrivee "
                + "FROM rendez_vous r JOIN patient p ON p.id_patient = r.patient_id "
                + "WHERE r.date_rendez_vous = ? AND r.statut = 'ARRIVE' "
                + "ORDER BY r.heure_arrivee, r.id_rendez_vous";
        List<PatientEnAttente> result = new ArrayList<>();
        try (Connection cn = DataSource.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(jour));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("heure_arrivee");
                    LocalDateTime arrivedAt = ts == null ? null : ts.toLocalDateTime();
                    result.add(new PatientEnAttente(rs.getLong("id_rendez_vous"),
                            rs.getLong("id_patient"), rs.getString("nom"), rs.getString("prenom"), arrivedAt));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement de la file d'attente", e);
        }
        return result;
    }

    @Override
    public List<RendezVousDuJour> findRendezVousDuJour(LocalDate jour) {
        String sql = "SELECT r.id_rendez_vous, p.id_patient, p.nom, p.prenom, r.heure_rendez_vous, r.statut "
                + "FROM rendez_vous r JOIN patient p ON p.id_patient = r.patient_id "
                + "WHERE r.date_rendez_vous = ? AND r.statut IN ('PLANIFIE','CONFIRME') "
                + "ORDER BY r.heure_rendez_vous, r.id_rendez_vous";
        List<RendezVousDuJour> result = new ArrayList<>();
        try (Connection cn = DataSource.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(jour));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new RendezVousDuJour(rs.getLong("id_rendez_vous"),
                            rs.getLong("id_patient"), rs.getString("nom"), rs.getString("prenom"),
                            rs.getTime("heure_rendez_vous").toLocalTime(), rs.getString("statut")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des rendez-vous du jour", e);
        }
        return result;
    }
}
