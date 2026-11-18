package ma.dental_ops.service.implementation;

import ma.dental_ops.common.security.AuthenticationException;
import ma.dental_ops.common.security.Session;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.api.AuthService;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {

    private static final String ERREUR_IDENTIFIANTS = "Adresse e-mail ou mot de passe incorrect.";

    private final UtilisateurRepository utilisateurRepository;

    public AuthServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Utilisateur login(String login, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login);

        // Message generique dans tous les cas (identifiant inconnu, mot de
        // passe errone ou compte inactif) pour ne jamais reveler quel champ
        // pose probleme, ni si le compte existe (CA-01).
        if (utilisateur == null
                || !utilisateur.isActif()
                || !BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
            throw new AuthenticationException(ERREUR_IDENTIFIANTS);
        }

        Session.getInstance().connecter(utilisateur);
        return utilisateur;
    }

    @Override
    public void logout() {
        Session.getInstance().deconnecter();
    }
}
