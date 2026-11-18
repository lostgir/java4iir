package ma.dental_ops.repository.api;

import ma.dental_ops.domain.Utilisateur;
import java.util.List;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    List<Utilisateur> findAllUtilisateurs();

    Utilisateur findUtilisateurById(Long idUtilisateur);

    Utilisateur findByLogin(String login);

    Utilisateur findByEmail(String email);

    Utilisateur save(Utilisateur utilisateur);

    void updateProfile(Long utilisateurId, String nom, String prenom, String telephone);
}