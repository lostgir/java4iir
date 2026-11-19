
package ma.dental_ops.repository.jdbcMySql;

import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.config.DataSource;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepositoryImplt implements UtilisateurRepository {

    private static final String SELECT_COLUMNS =
            "id_utilisateur, nom_utilisateur, prenom_utilisateur, " +
                    "email, mot_de_passe, statut_utilisateur";

    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        String sql = "SELECT " + SELECT_COLUMNS + " FROM utilisateur";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                utilisateurs.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des utilisateurs", e);
        }

        return utilisateurs;
    }

    @Override
    public Utilisateur findUtilisateurById(Long idUtilisateur) {
        String sql = "SELECT " + SELECT_COLUMNS +
                " FROM utilisateur WHERE id_utilisateur = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, idUtilisateur);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapRow(resultSet) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération de l'utilisateur " + idUtilisateur, e);
        }
    }

    @Override
    public Utilisateur findByLogin(String login) {
        // L'email sert d'identifiant de connexion dans la base.
        return findByEmail(login);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT " + SELECT_COLUMNS +
                " FROM utilisateur WHERE email = ?";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapRow(resultSet) : null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la recherche par email", e);
        }
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur " +
                "(nom_utilisateur, prenom_utilisateur, email, " +
                "mot_de_passe, statut_utilisateur) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataSource.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, utilisateur.getNomUtilisateur());
            statement.setString(2, utilisateur.getPrenomUtilisateur());
            statement.setString(3, utilisateur.getEmail());
            statement.setString(4, utilisateur.getMotDePasse());
            statement.setString(5, utilisateur.getStatutUtilisateur().name());

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

    private Utilisateur mapRow(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("email");

        return Utilisateur.builder()
                .idUtilisateur(resultSet.getLong("id_utilisateur"))
                .nomUtilisateur(resultSet.getString("nom_utilisateur"))
                .prenomUtilisateur(resultSet.getString("prenom_utilisateur"))
                .email(email)
                .login(email)
                .motDePasse(resultSet.getString("mot_de_passe"))
                .statutUtilisateur(
                        StatutUtilisateur.valueOf(
                                resultSet.getString("statut_utilisateur")))
                .build();
    }
}
