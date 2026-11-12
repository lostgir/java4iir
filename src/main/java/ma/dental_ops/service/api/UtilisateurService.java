package ma.dental_ops.service.api;

import ma.dental_ops.domain.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> findAll();

    Utilisateur updateProfile(Long utilisateurId, String nom, String prenom, String telephone);
}
