package ma.dental_ops.service.api;

import ma.dental_ops.domain.Utilisateur;

public interface AuthService {

    /**
     * CA-01 : un compte valide accede a son espace ; un mot de passe
     * incorrect est refuse sans reveler quel champ est en cause.
     */
    Utilisateur login(String login, String motDePasse);

    /**
     * CA-02 : apres deconnexion, les ecrans proteges exigent une nouvelle
     * authentification.
     */
    void logout();
}
