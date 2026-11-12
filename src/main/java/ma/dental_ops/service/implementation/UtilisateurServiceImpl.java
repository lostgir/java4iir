package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.dental_ops.service.api.UtilisateurService;

import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl() {
        this(new UtilisateurRepositoryImplt());
    }

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = java.util.Objects.requireNonNull(utilisateurRepository);
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAllUtilisateurs();
    }

    @Override
    public Utilisateur updateProfile(Long utilisateurId, String nom, String prenom, String telephone) {
        if (utilisateurId == null) throw new IllegalArgumentException("Utilisateur non identifié");
        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le nom et le prénom sont obligatoires");
        }
        utilisateurRepository.updateProfile(utilisateurId, nom.trim(), prenom.trim(),
                telephone == null || telephone.isBlank() ? null : telephone.trim());
        return utilisateurRepository.findUtilisateurById(utilisateurId);
    }
}
