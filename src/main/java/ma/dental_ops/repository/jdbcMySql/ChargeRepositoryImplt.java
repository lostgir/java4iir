package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.DataSource;
import ma.dental_ops.domain.Charge;
import ma.dental_ops.repository.api.ChargeRepository;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChargeRepositoryImplt implements ChargeRepository {

    private static final String SELECT_COLUMNS =
            "id_charge, titre, montant, date_charge, description, auteur_id";

    @Override
    public List<Charge> findAllCharges() {
        List<Charge> charges = new ArrayList<>();

        String sql = "SELECT " + SELECT_COLUMNS + " FROM charge";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                charges.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des charges", e);
        }

        return charges;
    }

    @Override
    public Charge findChargeById(Long idCharge) {
        String sql = "SELECT " + SELECT_COLUMNS +
                " FROM Charge WHERE id_charge = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idCharge);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la charge", e);
        }

        return null;
    }

    @Override
    public Charge save(Charge charge) {
        String sql = "INSERT INTO charge " +
                "(titre, montant, date_charge, description, auteur_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, charge.getTitre());
            statement.setBigDecimal(2, charge.getMontant());
            statement.setDate(3, Date.valueOf(charge.getDateCharge()));
            statement.setString(4, charge.getDescription());
            statement.setLong(5, charge.getAuteurId());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    charge.setIdCharge(keys.getLong(1));
                }
            }

            return charge;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la charge", e);
        }
    }

    @Override
    public List<Charge> findChargesParPeriode(
            LocalDate dateDebut, LocalDate dateFin) {

        List<Charge> charges = new ArrayList<>();

        String sql = "SELECT " + SELECT_COLUMNS +
                " FROM charge WHERE date_charge BETWEEN ? AND ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(dateDebut));
            statement.setDate(2, Date.valueOf(dateFin));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    charges.add(mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par période", e);
        }

        return charges;
    }

    @Override
    public BigDecimal calculerTotalCharges(
            LocalDate dateDebut, LocalDate dateFin) {

        String sql = "SELECT COALESCE(SUM(montant), 0) " +
                "FROM charge WHERE date_charge BETWEEN ? AND ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(dateDebut));
            statement.setDate(2, Date.valueOf(dateFin));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du total des charges", e);
        }

        return BigDecimal.ZERO;
    }

    private Charge mapRow(ResultSet resultSet) throws SQLException {
        return Charge.builder()
                .idCharge(resultSet.getLong("id_charge"))
                .titre(resultSet.getString("titre"))
                .montant(resultSet.getBigDecimal("montant"))
                .dateCharge(resultSet.getDate("date_charge").toLocalDate())
                .description(resultSet.getString("description"))
                .auteurId(resultSet.getLong("auteur_id"))
                .build();
    }
}