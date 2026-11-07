
package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.config.DataSource;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepositoryImplt implements UtilisateurRepository {

    private static final String SELECT_COLUMNS =
            "id_utilisateur, nom_utilisateur, prenom_utilisateur, statut_utilisateur";

    // Récupérer tous les utilisateurs
    @Override
    public List<Utilisateur> findAllUtilisateurs() {

        String sql = "SELECT " + SELECT_COLUMNS + " FROM utilisateur";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection connection = DataSource.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                utilisateurs.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des utilisateurs", e);
        }

        return utilisateurs;
    }

    // Récupérer un utilisateur par son ID
    @Override
    public Utilisateur findUtilisateurById(Long idUtilisateur) {

        String sql = "SELECT " + SELECT_COLUMNS
                + " FROM utilisateur WHERE id_utilisateur = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idUtilisateur);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapRow(resultSet) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération de l'utilisateur "
                            + idUtilisateur, e);
        }
    }

    // Enregistrer un nouvel utilisateur
    @Override
    public Utilisateur save(Utilisateur utilisateur) {

        String sql = "INSERT INTO utilisateur "
                + "(nom_utilisateur, prenom_utilisateur, statut_utilisateur) "
                + "VALUES (?, ?, ?)";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, utilisateur.getNomUtilisateur());
            statement.setString(2, utilisateur.getPrenomUtilisateur());
            statement.setString(3, utilisateur.getStatutUtilisateur().name());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    utilisateur.setIdUtilisateur(generatedKeys.getLong(1));
                }
            }

            return utilisateur;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de l'enregistrement de l'utilisateur", e);
        }
    }

    // Transformer une ligne SQL en objet Utilisateur
    private Utilisateur mapRow(ResultSet resultSet) throws SQLException {

        return Utilisateur.builder()
                .idUtilisateur(resultSet.getLong("id_utilisateur"))
                .nomUtilisateur(resultSet.getString("nom_utilisateur"))
                .prenomUtilisateur(resultSet.getString("prenom_utilisateur"))
                .statutUtilisateur(
                        StatutUtilisateur.valueOf(
                                resultSet.getString("statut_utilisateur")))
                .build();
    }
}
