package ma.oncf_ops.repository.api;

import ma.oncf_ops.domain.Utilisateur;

import java.util.List;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
    //Retourne tous les utilisateurs
    List<Utilisateur> findAllUtilisateurs();

    //Retourne l'utilisateur par son ID
    Utilisateur findUtilisateurById(Long idUtilisateur);

    //Enregistre un nouvel utilisateur dans la BD
    Utilisateur save(Utilisateur utilisateur);
}
