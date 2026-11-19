package ma.dental_ops.common.security;

import ma.dental_ops.common.enums.Droit;
import ma.dental_ops.common.enums.StatutUtilisateur;

import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Table de correspondance rôle -> droits accordés (RBAC statique),
 * conforme à la matrice des droits du cahier des charges (§1.4).
 *
 * <ul>
 *   <li>L'administrateur gère les comptes et son profil, sans accès aux
 *       patients, au dossier médical ni aux finances (PV-15).</li>
 *   <li>La secrétaire gère l'accueil, l'agenda et la caisse.</li>
 *   <li>Le médecin gère le suivi clinique et les charges, et consulte
 *       seulement la facturation (PV-07).</li>
 * </ul>
 * Un rôle absent de la table (ex. REGULATEUR) n'a aucun droit.
 */
public final class RoleDroits {

    private static final Map<StatutUtilisateur, Set<Droit>> DROITS_PAR_ROLE = new EnumMap<>(StatutUtilisateur.class);

    static {
        DROITS_PAR_ROLE.put(StatutUtilisateur.ADMINISTRATEUR, EnumSet.of(
                Droit.GERER_PROFIL,
                Droit.GERER_UTILISATEURS
        ));
        DROITS_PAR_ROLE.put(StatutUtilisateur.SECRETAIRE, EnumSet.of(
                Droit.GERER_PROFIL,
                Droit.GERER_PATIENTS,
                Droit.GERER_AGENDA,
                Droit.GERER_FINANCES,
                Droit.CONSULTER_FINANCES,
                Droit.CONSULTER_TABLEAU_BORD
        ));
        DROITS_PAR_ROLE.put(StatutUtilisateur.MEDECIN, EnumSet.of(
                Droit.GERER_PROFIL,
                Droit.GERER_PATIENTS,
                Droit.GERER_DOSSIER_MEDICAL,
                Droit.GERER_AGENDA,
                Droit.GERER_CONSULTATIONS,
                Droit.GERER_DOCUMENTS_MEDICAUX,
                Droit.CONSULTER_FINANCES,
                Droit.CONSULTER_TABLEAU_BORD,
                Droit.GERER_CHARGES,
                Droit.GERER_REFERENTIELS
        ));
    }

    private RoleDroits() {
    }

    public static boolean accorde(StatutUtilisateur role, Droit droit) {
        return role != null && droit != null
                && DROITS_PAR_ROLE.getOrDefault(role, Set.of()).contains(droit);
    }

    /** Droits d'un rôle, en lecture seule (vide si le rôle n'en a aucun). */
    public static Set<Droit> droitsDe(StatutUtilisateur role) {
        Set<Droit> droits = role == null ? null : DROITS_PAR_ROLE.get(role);
        return droits == null ? Set.of() : Collections.unmodifiableSet(droits);
    }
}
