package ma.dental_ops.ui.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import ma.dental_ops.service.api.UtilisateurService;
import ma.dental_ops.domain.Utilisateur;

@Data
@AllArgsConstructor
public class UtilisateurController {

    private UtilisateurService service;

    public UtilisateurController() {

    }

    public Utilisateur enregistrerProfil(Long id, String nom, String prenom, String telephone) {
        if (service == null) throw new IllegalStateException("Service utilisateur non configuré");
        return service.updateProfile(id, nom, prenom, telephone);
    }
}
