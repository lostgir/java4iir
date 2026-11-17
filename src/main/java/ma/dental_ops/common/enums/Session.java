
package ma.dental_ops.common.enums;

import ma.dental_ops.domain.Utilisateur;

public final class Session {

    private static Utilisateur utilisateurConnecte;

    private Session() {
        // Classe utilitaire : pas d'instanciation
    }

    // Enregistrer l'utilisateur connecté
    public static void ouvrirSession(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    // Récupérer l'utilisateur connecté
    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    // Vérifier si une session existe
    public static boolean estConnecte() {
        return utilisateurConnecte != null;
    }

    // Fermer la session
    public static void fermerSession() {
        utilisateurConnecte = null;
    }
}
