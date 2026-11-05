package ma.dental_ops.service.implementation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.api.UtilisateurService;

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
