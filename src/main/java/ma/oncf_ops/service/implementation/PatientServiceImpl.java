package ma.oncf_ops.service.implementation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.oncf_ops.domain.Utilisateur;
import ma.oncf_ops.repository.api.UtilisateurRepository;
import ma.oncf_ops.service.api.UtilisateurService;

import java.util.List;

@Data
@AllArgsConstructor
public class PatientServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public List<Utilisateur> findAll() {
        return List.of();
    }
}
