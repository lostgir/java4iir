package ma.dental_ops;

import junit.framework.TestCase;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.common.security.AuthenticationException;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.service.implementation.AuthServiceImpl;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

public class AuthServiceTest extends TestCase {

    private Utilisateur creerUtilisateur(boolean actif) {
        return Utilisateur.builder()
                .idUtilisateur(5L)
                .nomUtilisateur("Test")
                .prenomUtilisateur("User")
                .login("test")
                .email("test@example.com")
                .telephone("0600000000")
                .motDePasse(BCrypt.hashpw("pass123", BCrypt.gensalt()))
                .statutUtilisateur(StatutUtilisateur.SECRETAIRE)
                .actif(actif)
                .build();
    }

    public void testConnexionParLogin() {
        Utilisateur user = creerUtilisateur(true);
        AuthServiceImpl service =
                new AuthServiceImpl(new FakeRepository(user));

        Utilisateur resultat = service.login("test", "pass123");

        assertNotNull(resultat);
        assertEquals(Long.valueOf(5L), resultat.getIdUtilisateur());
        assertEquals("test", resultat.getLogin());
        assertEquals("test@example.com", resultat.getEmail());
        assertEquals(
                StatutUtilisateur.SECRETAIRE,
                resultat.getStatutUtilisateur()
        );
    }

    public void testRefusMotDePasseIncorrect() {
        AuthServiceImpl service =
                new AuthServiceImpl(new FakeRepository(creerUtilisateur(true)));

        try {
            service.login("test", "incorrect");
            fail("une AuthenticationException etait attendue");
        } catch (AuthenticationException expected) {
            // CA-01 : message generique, ne revele pas quel champ est en cause.
        }
    }

    public void testRefusLoginInconnu() {
        AuthServiceImpl service =
                new AuthServiceImpl(new FakeRepository(creerUtilisateur(true)));

        try {
            service.login("inconnu", "pass123");
            fail("une AuthenticationException etait attendue");
        } catch (AuthenticationException expected) {
            // attendu
        }
    }

    public void testRefusCompteInactif() {
        AuthServiceImpl service =
                new AuthServiceImpl(new FakeRepository(creerUtilisateur(false)));

        try {
            service.login("test", "pass123");
            fail("une AuthenticationException etait attendue");
        } catch (AuthenticationException expected) {
            // CA-01 : un compte inactif est refuse malgre un mot de passe correct.
        }
    }

    private static final class FakeRepository
            implements UtilisateurRepository {

        private final Utilisateur user;

        private FakeRepository(Utilisateur user) {
            this.user = user;
        }

        @Override
        public List<Utilisateur> findAllUtilisateurs() {
            return List.of(user);
        }

        @Override
        public Utilisateur findUtilisateurById(Long id) {
            return Objects.equals(user.getIdUtilisateur(), id) ? user : null;
        }

        @Override
        public Utilisateur findByLogin(String login) {
            return Objects.equals(user.getLogin(), login) ? user : null;
        }

        @Override
        public Utilisateur findByEmail(String email) {
            return Objects.equals(user.getEmail(), email) ? user : null;
        }

        @Override
        public Utilisateur save(Utilisateur value) {
            return value;
        }

        @Override
        public void updateProfile(
                Long id, String nom, String prenom, String telephone) {
            user.setNomUtilisateur(nom);
            user.setPrenomUtilisateur(prenom);
            user.setTelephone(telephone);
        }
    }
}
