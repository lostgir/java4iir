package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.common.enums.Droit;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.common.security.AccesRefuseException;
import ma.dental_ops.common.security.RoleDroits;
import ma.dental_ops.common.security.Session;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.service.implementation.AuthServiceImpl;

/**
 * Matrice des droits par rôle (§1.4), session requise par exiger()
 * et plus d'accès après déconnexion (CA-02).
 */
public class SecuriteDroitsTest extends TestCase {

    @Override
    protected void tearDown() {
        Session.getInstance().deconnecter();
    }

    public void testAdministrateurSansAccesAuxPatientsNiAuxFinances() {
        assertTrue(RoleDroits.accorde(StatutUtilisateur.ADMINISTRATEUR, Droit.GERER_UTILISATEURS));
        assertTrue(RoleDroits.accorde(StatutUtilisateur.ADMINISTRATEUR, Droit.GERER_PROFIL));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.ADMINISTRATEUR, Droit.GERER_PATIENTS));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.ADMINISTRATEUR, Droit.GERER_DOSSIER_MEDICAL));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.ADMINISTRATEUR, Droit.CONSULTER_FINANCES));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.ADMINISTRATEUR, Droit.CONSULTER_TABLEAU_BORD));
    }

    public void testSecretaireSansDossierMedicalNiCharges() {
        assertTrue(RoleDroits.accorde(StatutUtilisateur.SECRETAIRE, Droit.GERER_PATIENTS));
        assertTrue(RoleDroits.accorde(StatutUtilisateur.SECRETAIRE, Droit.GERER_FINANCES));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.SECRETAIRE, Droit.GERER_DOSSIER_MEDICAL));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.SECRETAIRE, Droit.GERER_CHARGES));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.SECRETAIRE, Droit.GERER_UTILISATEURS));
    }

    public void testMedecinConsulteLaFacturationSansLaGerer() {
        assertTrue(RoleDroits.accorde(StatutUtilisateur.MEDECIN, Droit.GERER_DOSSIER_MEDICAL));
        assertTrue(RoleDroits.accorde(StatutUtilisateur.MEDECIN, Droit.CONSULTER_FINANCES));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.MEDECIN, Droit.GERER_FINANCES));
        assertFalse(RoleDroits.accorde(StatutUtilisateur.MEDECIN, Droit.GERER_UTILISATEURS));
    }

    public void testRoleInconnuOuAbsentSansAucunDroit() {
        for (Droit droit : Droit.values()) {
            assertFalse(RoleDroits.accorde(StatutUtilisateur.REGULATEUR, droit));
            assertFalse(RoleDroits.accorde(null, droit));
        }
    }

    public void testAucuneActionSansSession() {
        try {
            Session.getInstance().exiger(Droit.GERER_PATIENTS);
            fail("Une action sans session doit être refusée.");
        } catch (AccesRefuseException expected) {
            assertEquals(Session.MESSAGE_NON_CONNECTE, expected.getMessage());
        }
    }

    public void testPlusAucunAccesApresDeconnexion() {
        Session.getInstance().connecter(utilisateur(3L, StatutUtilisateur.MEDECIN));
        Session.getInstance().exiger(Droit.GERER_PATIENTS);

        new AuthServiceImpl(null).logout();

        assertFalse(Session.getInstance().estConnecte());
        try {
            Session.getInstance().exiger(Droit.GERER_PATIENTS);
            fail("Après déconnexion, l'accès doit être refusé.");
        } catch (AccesRefuseException expected) {
            assertEquals(Session.MESSAGE_NON_CONNECTE, expected.getMessage());
        }
    }

    public void testRefusResteUneSecurityException() {
        // Les écrans qui interceptent déjà SecurityException continuent de fonctionner.
        assertTrue(new AccesRefuseException("x") instanceof SecurityException);
    }

    private Utilisateur utilisateur(Long id, StatutUtilisateur role) {
        return Utilisateur.builder().idUtilisateur(id).statutUtilisateur(role).actif(true).build();
    }
}
