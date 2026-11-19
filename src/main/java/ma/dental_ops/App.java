
package ma.dental_ops;

import ma.dental_ops.common.enums.Session;
import ma.dental_ops.common.enums.StatutUtilisateur;
import ma.dental_ops.config.ThemeConfig;
import ma.dental_ops.domain.Utilisateur;
import ma.dental_ops.ui.view.MainWindow;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {

        ThemeConfig.applyTheme();

        SwingUtilities.invokeLater(() -> {

            // Utilisateur temporaire pour prévisualiser MainWindow
            Utilisateur utilisateur = Utilisateur.builder()
                    .idUtilisateur(1L)
                    .nomUtilisateur("Admin")
                    .prenomUtilisateur("DentalSoft")
                    .email("admin@dentalsoft.ma")
                    .statutUtilisateur(StatutUtilisateur.ADMINISTRATEUR)
                    .build();

            Session.ouvrirSession(utilisateur);

            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
