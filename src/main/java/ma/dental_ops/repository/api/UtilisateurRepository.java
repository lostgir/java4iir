package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Utilisateur;

import java.util.List;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
    //Retourne tous les utilisateurs
    List<Utilisateur> findAllUtilisateurs();

    //Retourne l'utilisateur par son ID
    Utilisateur findUtilisateurById(Long idUtilisateur);

    //Retourne l'utilisateur par son identifiant de connexion (authentification)
    Utilisateur findByLogin(String login);

    //Enregistre un nouvel utilisateur dans la BD
    Utilisateur save(Utilisateur utilisateur);
}
