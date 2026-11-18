package ma.dental_ops.service.api;

import ma.dental_ops.domain.Utilisateur;

public interface AuthService {
    Utilisateur authentifier(String email, char[] motDePasse);
}
