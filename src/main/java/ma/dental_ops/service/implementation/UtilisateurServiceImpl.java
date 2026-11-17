package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.dental_ops.service.api.UtilisateurService;

import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl() {
        this.utilisateurRepository = new UtilisateurRepositoryImplt();
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAllUtilisateurs();
    }
}