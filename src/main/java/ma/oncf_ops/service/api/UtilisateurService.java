package ma.oncf_ops.service.api;

import ma.oncf_ops.domain.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    List<Utilisateur> findAll();
}
