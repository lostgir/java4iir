package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.implementation.AuthServiceImpl;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class AuthServiceTest extends TestCase {
    public void testAuthenticatesUserWithoutChangingIdentityFields() {
        Utilisateur user = Utilisateur.builder().idUtilisateur(5L).nomUtilisateur("Test")
                .prenomUtilisateur("User").email("test@example.com")
                .motDePasse(BCrypt.hashpw("pass123", BCrypt.gensalt()))
                .statutUtilisateur(StatutUtilisateur.SECRETAIRE).telephone("0600000000").build();
        AuthServiceImpl service = new AuthServiceImpl(new FakeRepository(user));

        Utilisateur authenticated = service.authentifier(" test@example.com ", "pass123".toCharArray());

        assertNotNull(authenticated);
        assertEquals(Long.valueOf(5L), authenticated.getIdUtilisateur());
        assertEquals(StatutUtilisateur.SECRETAIRE, authenticated.getStatutUtilisateur());
    }

    public void testRejectsWrongPasswordAndUnknownEmail() {
        Utilisateur user = Utilisateur.builder().email("test@example.com")
                .motDePasse(BCrypt.hashpw("pass123", BCrypt.gensalt())).build();
        AuthServiceImpl service = new AuthServiceImpl(new FakeRepository(user));
        assertNull(service.authentifier("test@example.com", "wrong".toCharArray()));
        assertNull(service.authentifier("unknown@example.com", "pass123".toCharArray()));
    }

    private static final class FakeRepository implements UtilisateurRepository {
        private final Utilisateur user;
        private FakeRepository(Utilisateur user) { this.user = user; }
        @Override public List<Utilisateur> findAllUtilisateurs() { return List.of(user); }
        @Override public Utilisateur findUtilisateurById(Long id) { return user.getIdUtilisateur().equals(id) ? user : null; }
        @Override public Utilisateur findByEmail(String email) { return user.getEmail().equals(email) ? user : null; }
        @Override public Utilisateur save(Utilisateur value) { return value; }
        @Override public void updateProfile(Long id, String nom, String prenom, String telephone) { }
    }
}
