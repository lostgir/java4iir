package ma.dental_ops.common.enums;

/**
 * Droits applicatifs, un par module du cahier des charges (§1.4).
 * G = gestion, C = consultation seule.
 */
public enum Droit {
    /** Authentification et profil personnel (G pour tous les rôles). */
    GERER_PROFIL,
    /** Comptes, rôles et paramètres du cabinet (administrateur). */
    GERER_UTILISATEURS,
    /** Fiches patients : création, recherche, modification, archivage. */
    GERER_PATIENTS,
    /** Dossier médical (médecin). */
    GERER_DOSSIER_MEDICAL,
    /** Agenda, rendez-vous, arrivée et file d'attente. */
    GERER_AGENDA,
    /** Consultations et interventions (médecin). */
    GERER_CONSULTATIONS,
    /** Ordonnances et certificats (médecin). */
    GERER_DOCUMENTS_MEDICAUX,
    /** Facturation et paiements : créer, valider, encaisser (secrétaire). */
    GERER_FINANCES,
    /** Facturation et paiements en lecture (secrétaire, médecin : PV-07). */
    CONSULTER_FINANCES,
    /** Tableau de bord et indicateurs d'activité. */
    CONSULTER_TABLEAU_BORD,
    /** Charges du cabinet (médecin). */
    GERER_CHARGES,
    /** Référentiels des actes et des médicaments (médecin). */
    GERER_REFERENTIELS
}
