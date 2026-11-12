
package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.api.UtilisateurService;

import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository repository;

    public UtilisateurServiceImpl(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Utilisateur> findAll() {
        return repository.findAllUtilisateurs();
    }
}
