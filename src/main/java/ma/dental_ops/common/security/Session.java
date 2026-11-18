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
        Utilisateur utilisateur = exigerConnexion();
        if (!RoleDroits.accorde(utilisateur.getStatutUtilisateur(), droit)) {
            throw new AccesRefuseException(MESSAGE_DROIT_REFUSE);
        }
    }

    /**
     * RG-03 : aucun service sans session ouverte. Renvoie l'utilisateur
     * connecté, ou refuse l'accès si la session est fermée.
     */
    public Utilisateur exigerConnexion() {
        Utilisateur utilisateur = utilisateurConnecte;
        if (utilisateur == null) {
            throw new AccesRefuseException(MESSAGE_NON_CONNECTE);
        }
        return utilisateur;
    }

    /** Vrai si l'utilisateur connecté dispose du droit (sert à masquer les menus). */
    public boolean peut(Droit droit) {
        Utilisateur utilisateur = utilisateurConnecte;
        return utilisateur != null && RoleDroits.accorde(utilisateur.getStatutUtilisateur(), droit);
    }

    public static final String MESSAGE_NON_CONNECTE =
            "Vous devez être connecté pour effectuer cette action.";
    public static final String MESSAGE_DROIT_REFUSE =
            "Votre rôle ne vous autorise pas à effectuer cette action.";
}
