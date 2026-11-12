package ma.dental_ops;

import ma.dental_ops.repository.jdbcMySql.UtilisateurRepositoryImplt;
import ma.dental_ops.service.implementation.AuthServiceImpl;
import ma.dental_ops.ui.view.LoginView;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView(
                    new AuthServiceImpl(new UtilisateurRepositoryImplt()));
            loginView.setVisible(true);
        });
    }
}
