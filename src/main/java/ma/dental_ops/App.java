package ma.dental_ops;

import ma.dental_ops.repository.api.UtilisateurRepository;
import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.dental_ops.service.api.AuthService;
import ma.dental_ops.service.implementation.AuthServiceImpl;
import ma.dental_ops.ui.controller.LoginController;

import javax.swing.SwingUtilities;

/**
 * Point d'entree de l'application DentalSoft.
 */
public class App {
    public static void main(String[] args) {
        UtilisateurRepository utilisateurRepository = new UtilisateurRepositoryImplt();
        AuthService authService = new AuthServiceImpl(utilisateurRepository);
        LoginController loginController = new LoginController(authService);

        SwingUtilities.invokeLater(loginController::start);
    }
}
