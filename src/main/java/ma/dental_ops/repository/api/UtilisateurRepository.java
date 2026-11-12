package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Utilisateur;

import java.util.List;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    // Retourner tous les utilisateurs
    List<Utilisateur> findAllUtilisateurs();

    // Retourner un utilisateur par son ID
    Utilisateur findUtilisateurById(Long idUtilisateur);

    // Retrouver un utilisateur par son email
    Utilisateur findByEmail(String email);

    // Enregistrer un nouvel utilisateur
    Utilisateur save(Utilisateur utilisateur);

    void updateProfile(Long utilisateurId, String nom, String prenom, String telephone);
}
