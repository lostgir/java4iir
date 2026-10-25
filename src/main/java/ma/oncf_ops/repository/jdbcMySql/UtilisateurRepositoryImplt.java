package ma.oncf_ops.repository.jdbcMySql;

import ma.oncf_ops.domain.Utilisateur;
import ma.oncf_ops.repository.api.UtilisateurRepository;

import java.util.List;

public class UtilisateurRepositoryImplt implements UtilisateurRepository {
    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return List.of();
    }

    @Override
    public Utilisateur findUtilisateurById(Long idUtilisateur) {
        return null;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return null;
    }
}
