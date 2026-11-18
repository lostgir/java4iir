package ma.dental_ops.ui.controller;

import ma.dental_ops.common.security.AuthenticationException;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.service.api.AuthService;
import ma.dental_ops.ui.view.AccueilView;
import ma.dental_ops.ui.view.LoginView;

import javax.swing.JOptionPane;

/**
 * T12 : relie l'ecran de connexion (E-07) a l'AuthService et gere la
 * deconnexion (CA-02 : retour a un ecran de connexion qui exige une
 * nouvelle authentification).
 */
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    public void start() {
        showLoginView();
    }

    private void showLoginView() {
        LoginView view = new LoginView();

        view.getLoginButton().addActionListener(e -> {
            view.clearError();
            try {
                Utilisateur utilisateur = authService.login(view.getEmail(), view.getPassword());
                view.dispose();
                showAccueilView(utilisateur);
            } catch (AuthenticationException ex) {
                view.showError(ex.getMessage());
            }
        });

        view.getForgotPasswordLink().addActionListener(e -> JOptionPane.showMessageDialog(view,
                "Veuillez contacter l'administrateur du cabinet pour reinitialiser votre mot de passe.",
                "Mot de passe oublié",
                JOptionPane.INFORMATION_MESSAGE));

        view.setVisible(true);
    }

    private void showAccueilView(Utilisateur utilisateur) {
        AccueilView accueil = new AccueilView(utilisateur);
        accueil.getLogoutButton().addActionListener(e -> {
            authService.logout();
            accueil.dispose();
            showLoginView();
        });
        accueil.setVisible(true);
    }
}
