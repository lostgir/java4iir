package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.implementation.UtilisateurServiceImpl;

import java.util.List;

public class UtilisateurServiceTest extends TestCase {
    public void testProfileUpdatePersistsCoordinatesAndLeavesLoginAndRoleUntouched() {
        Utilisateur stored = Utilisateur.builder().idUtilisateur(9L)
                .nomUtilisateur("Avant").prenomUtilisateur("Test")
                .email("identifiant@example.com").telephone("0600000000")
                .motDePasse("hash").statutUtilisateur(StatutUtilisateur.SECRETAIRE).build();
        FakeRepository repository = new FakeRepository(stored);
        UtilisateurServiceImpl service = new UtilisateurServiceImpl(repository);

        Utilisateur updated = service.updateProfile(9L, "  Nouveau ", " Prénom ", " 0612345678 ");

        assertEquals("Nouveau", updated.getNomUtilisateur());
        assertEquals("Prénom", updated.getPrenomUtilisateur());
        assertEquals("0612345678", updated.getTelephone());
        assertEquals("identifiant@example.com", updated.getEmail());
        assertEquals(StatutUtilisateur.SECRETAIRE, updated.getStatutUtilisateur());
        assertEquals("hash", updated.getMotDePasse());
        assertEquals(9L, repository.lastUpdatedId);
    }

    public void testRequiredNamesCannotBeBlank() {
        FakeRepository repository = new FakeRepository(Utilisateur.builder().idUtilisateur(1L).build());
        UtilisateurServiceImpl service = new UtilisateurServiceImpl(repository);
        try {
            service.updateProfile(1L, " ", "Nom", "0600000000");
            fail("Expected a blank name to be rejected");
        } catch (IllegalArgumentException expected) {
            assertEquals(-1L, repository.lastUpdatedId);
        }
    }

    private static final class FakeRepository implements UtilisateurRepository {
        private final Utilisateur stored;
        private long lastUpdatedId = -1;
        private FakeRepository(Utilisateur stored) { this.stored = stored; }
        @Override public List<Utilisateur> findAllUtilisateurs() { return List.of(stored); }
        @Override public Utilisateur findUtilisateurById(Long id) { return stored.getIdUtilisateur().equals(id) ? stored : null; }
        @Override public Utilisateur findByEmail(String email) { return stored.getEmail().equals(email) ? stored : null; }
        @Override public Utilisateur save(Utilisateur utilisateur) { return utilisateur; }
        @Override public void updateProfile(Long id, String nom, String prenom, String telephone) {
            lastUpdatedId = id;
            stored.setNomUtilisateur(nom);
            stored.setPrenomUtilisateur(prenom);
            stored.setTelephone(telephone);
        }
    }
}
