package ma.dental_ops;

import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.dental_ops.service.implementation.AuthServiceImpl;
import ma.dental_ops.ui.controller.LoginController;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UtilisateurRepositoryImplt repository =
                    new UtilisateurRepositoryImplt();

            AuthServiceImpl authService =
                    new AuthServiceImpl(repository);

            new LoginController(authService).start();
        });
    }
}