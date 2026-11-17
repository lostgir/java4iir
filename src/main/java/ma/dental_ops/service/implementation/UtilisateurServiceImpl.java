 houda

 main
package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
 houda

import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
main
import ma.dental_ops.service.api.UtilisateurService;

import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

 houda
    private final UtilisateurRepository repository;

    public UtilisateurServiceImpl(UtilisateurRepository repository) {
        this.repository = repository;

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl() {
        this.utilisateurRepository = new UtilisateurRepositoryImplt();
 main
    }

    @Override
    public List<Utilisateur> findAll() {
 houda
        return repository.findAllUtilisateurs();
    }
}

        return utilisateurRepository.findAllUtilisateurs();
    }
}
 main
