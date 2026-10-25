package ma.oncf_ops.ui.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import ma.oncf_ops.service.api.UtilisateurService;

@Data
@AllArgsConstructor
public class UtilisateurController {

    private UtilisateurService service;

    public UtilisateurController() {

    }
}
