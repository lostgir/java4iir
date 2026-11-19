package ma.dental_ops;

import ma.dental_ops.config.ApplicationContext;
import ma.dental_ops.service.api.AuthService;
import ma.dental_ops.ui.controller.LoginController;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthService authService = ApplicationContext.getInstance().getBean(AuthService.class);

            new LoginController(authService).start();
        });
    }
}
