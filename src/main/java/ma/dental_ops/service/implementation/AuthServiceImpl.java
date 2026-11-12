package ma.dental_ops.service.implementation;

import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.api.AuthService;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class AuthServiceImpl implements AuthService {
    private final UtilisateurRepository repository;

    public AuthServiceImpl(UtilisateurRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Utilisateur authentifier(String email, char[] motDePasse) {
        if (email == null || email.isBlank() || motDePasse == null || motDePasse.length == 0) return null;
        Utilisateur utilisateur = repository.findByEmail(email.trim());
        if (utilisateur == null || utilisateur.getMotDePasse() == null) return null;
        try {
            return BCrypt.checkpw(new String(motDePasse), utilisateur.getMotDePasse()) ? utilisateur : null;
        } catch (IllegalArgumentException invalidHash) {
            return null;
        }
    }
}
