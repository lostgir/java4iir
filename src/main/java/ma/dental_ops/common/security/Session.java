package ma.dental_ops.common.security;

import ma.dental_ops.common.enums.Droit;
import ma.dental_ops.domain.Utilisateur;

/**
 * Session utilisateur courante (mono-poste). CA-02 : apres deconnexion, les
 * ecrans proteges exigent une nouvelle authentification -> exiger() refuse
 * tout accès une fois l'utilisateur deconnecte.
 */
public final class Session {

    private static final Session INSTANCE = new Session();

    private Utilisateur utilisateurConnecte;

    private Session() {
    }

    public static Session getInstance() {
        return INSTANCE;
    }

    public void connecter(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }

    public void deconnecter() {
        this.utilisateurConnecte = null;
    }

    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    /**
     * BNF-01 : toute action non autorisee doit etre refusee dans la couche
     * Service, avant toute ecriture. Les services metier doivent appeler
     * cette methode avant d'effectuer une operation protegee.
     */
    public void exiger(Droit droit) {
        if (!estConnecte()) {
            throw new AccesRefuseException("Vous devez etre connecte pour effectuer cette action.");
        }
        if (!RoleDroits.accorde(utilisateurConnecte.getStatutUtilisateur(), droit)) {
            throw new AccesRefuseException("Votre role ne vous autorise pas a effectuer cette action.");
        }
    }
}
