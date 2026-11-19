package ma.dental_ops;

import ma.dental_ops.config.ApplicationContext;
import ma.dental_ops.service.api.AuthService;
import ma.dental_ops.ui.view.LoginView;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationContext context = ApplicationContext.getInstance();
            AuthService authService = context.getBean(AuthService.class);

            LoginView loginView = new LoginView(authService);
            loginView.setVisible(true);
        });
    }
}
