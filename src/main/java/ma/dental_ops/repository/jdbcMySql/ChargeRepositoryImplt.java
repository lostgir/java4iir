package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.config.JdbcConnectionFactory;
import ma.dental_ops.domain.Charge;
import ma.dental_ops.dto.PageResult;
import ma.dental_ops.repository.api.ChargeRepository;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChargeRepositoryImplt implements ChargeRepository {

    private final JdbcConnectionFactory connectionFactory;

    public ChargeRepositoryImplt() {
        this(JdbcConnectionFactory.getInstance());
    }

    public ChargeRepositoryImplt(JdbcConnectionFactory connectionFactory) {
        this.connectionFactory = java.util.Objects.requireNonNull(connectionFactory);
    }

    private static final String SELECT_COLUMNS =
            "id_charge, titre, montant, date_charge, description, auteur_id";

    @Override
    public List<Charge> findAllCharges() {
        List<Charge> charges = new ArrayList<>();

        String sql = "SELECT " + SELECT_COLUMNS + " FROM charge";

        try (Connection connection = connectionFactory.createConnection();
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

        try (Connection connection = connectionFactory.createConnection();
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

        try (Connection connection = connectionFactory.createConnection();
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

        try (Connection connection = connectionFactory.createConnection();
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
    public PageResult<Charge> findChargesParPeriode(
            LocalDate dateDebut, LocalDate dateFin, int page, int pageSize) {
        if (page < 0 || pageSize < 1 || pageSize > 500) {
            throw new IllegalArgumentException("La page doit être positive et la taille comprise entre 1 et 500.");
        }
        String filter = " FROM charge WHERE date_charge BETWEEN ? AND ?";
        long total;
        List<Charge> charges = new ArrayList<>();
        try (Connection connection = connectionFactory.createConnection()) {
            try (PreparedStatement count = connection.prepareStatement("SELECT COUNT(*)" + filter)) {
                count.setDate(1, Date.valueOf(dateDebut));
                count.setDate(2, Date.valueOf(dateFin));
                try (ResultSet result = count.executeQuery()) {
                    result.next();
                    total = result.getLong(1);
                }
            }
            String sql = "SELECT " + SELECT_COLUMNS + filter
                    + " ORDER BY date_charge DESC, id_charge DESC LIMIT ? OFFSET ?";
            try (PreparedStatement rows = connection.prepareStatement(sql)) {
                rows.setDate(1, Date.valueOf(dateDebut));
                rows.setDate(2, Date.valueOf(dateFin));
                rows.setInt(3, pageSize);
                rows.setLong(4, (long) page * pageSize);
                try (ResultSet result = rows.executeQuery()) {
                    while (result.next()) charges.add(mapRow(result));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche paginée des charges", e);
        }
        return new PageResult<>(charges, page, pageSize, total);
    }

    @Override
    public BigDecimal calculerTotalCharges(
            LocalDate dateDebut, LocalDate dateFin) {

        String sql = "SELECT COALESCE(SUM(montant), 0) " +
                "FROM charge WHERE date_charge BETWEEN ? AND ?";

        try (Connection connection = connectionFactory.createConnection();
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

    @Override
    public BigDecimal calculerSolde(LocalDate debut, LocalDate fin) {
        String sql = """
                SELECT
                    (SELECT COALESCE(SUM(montant), 0) FROM paiement
                     WHERE statut = 'PAYE' AND date_paiement BETWEEN ? AND ?)
                  - (SELECT COALESCE(SUM(montant), 0) FROM charge
                     WHERE date_charge BETWEEN ? AND ?) AS solde
                """;
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(debut));
            statement.setDate(2, Date.valueOf(fin));
            statement.setDate(3, Date.valueOf(debut));
            statement.setDate(4, Date.valueOf(fin));
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? result.getBigDecimal("solde") : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du solde", e);
        }
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
